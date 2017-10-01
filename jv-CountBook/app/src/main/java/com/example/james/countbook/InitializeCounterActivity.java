package com.example.james.countbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;



public class InitializeCounterActivity extends AppCompatActivity {

    private Counter counter;
    private ArrayList<Counter> countBook;
    private static final String FILENAME = "file.sav";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialize_counter);

        Intent intent = getIntent();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();

    }

    // taken from lonelytwitter labs
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(countBook, out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            //Taken from https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2017-09-19
            Type listType = new TypeToken<ArrayList<Counter>>() {
            }.getType();
            countBook = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            countBook = new ArrayList<Counter>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
    // initialize counter object to pass into listview
    public void createCounter(View view) {

        EditText editText = (EditText) findViewById(R.id.counterName);
        String counterName = editText.getText().toString();

        // exception handling for blank counterName field
        if (counterName.equals("")) {
            return;
        }

        // grab initial counter value and convert to string
        EditText editText1 = (EditText) findViewById(R.id.initialCounter);
        String value = editText1.getText().toString();
        // prevents app from crashing if no value in initialCounter
        try {
            Integer initialCounterValue = Integer.valueOf(value); // convert string value to integer for construction of counter
        }

        catch(Exception e) {
            return;
        }
        Integer initialCounterValue = Integer.valueOf(value);

        // grab counterComment string
        EditText editText2 = (EditText) findViewById(R.id.counterComment);
        String counterComment = editText2.getText().toString();

        // initialize counter object and add to countBook list
        counter = new Counter (counterName, initialCounterValue, counterComment);
        countBook.add(counter);
        saveInFile(); // save to file to load in other activities

        finish(); // return to parent activity
    }
}

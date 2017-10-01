package com.example.james.countbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class EditCounterActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private ArrayList<Counter> countBook;
    private Integer position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_counter);

    }
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        System.out.println("before loadFromFile");
        loadFromFile();

        // access position of count object to be edited in order to grab attributes
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);

        // set name to box
        EditText editText = (EditText) findViewById(R.id.nameString);
        //editText.setText(String.valueOf(countBook.get(position).getName()));
        editText.setText(countBook.get(position).getName());


        // set initial counter value to box
        EditText editText2 = (EditText) findViewById(R.id.initialValue);
        editText2.setText(String.valueOf(countBook.get(position).getInitValue()));


        // set current counter value to box
        EditText editText3 = (EditText) findViewById(R.id.currentValue);
        editText3.setText(String.valueOf(countBook.get(position).getCurrValue()));

        // set comment string to box
        EditText editText4 = (EditText) findViewById(R.id.commentString);
        editText4.setText(countBook.get(position).getComment());


    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            //Taken from https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2017-09-19
            Type listType = new TypeToken<ArrayList<Counter>>(){}.getType();
            countBook = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            countBook = new ArrayList<Counter>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

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

    // update the currentValue field
    public void updateCounter() {
        //Integer counterValue = this.counter.getCurrValue();
        //countBook.get(position);

        TextView textView = (TextView) findViewById(R.id.currentValue);
        textView.setText(String.valueOf(countBook.get(position).getCurrValue()));
    }

    // this function is used to grab info from text boxes and edit counter attributes
    // onclick for save
    // return to parent activity after
    public void saveCounter(View view) {

        // grab name
        EditText editText = (EditText) findViewById(R.id.nameString);
        String counterName = editText.getText().toString();

        // exception handling for blank nameString field
        if (counterName.equals("")) {
            return;
        }

        // grab initialValue
        EditText editText2 = (EditText) findViewById(R.id.initialValue);
        String value = editText2.getText().toString();

        // prevents app from crashing if no value in initialCounter
        try {
            Integer initialCounterValue = Integer.valueOf(value); // convert string value to integer for construction of counter
        }

        catch(Exception e) {
            return;
        }
        Integer initialCounterValue = Integer.valueOf(value);

        // grab currentValue
        EditText editText3 = (EditText) findViewById(R.id.currentValue);
        String value2 = editText3.getText().toString();

        try {
            Integer currentCounterValue = Integer.valueOf(value2); // convert string value to integer for construction of counter
        }

        catch(Exception e) {
            return;
        }
        Integer currentCounterValue = Integer.valueOf(value2);

        // grab comment
        EditText editText4 = (EditText) findViewById(R.id.commentString);
        String counterComment = editText4.getText().toString();

        // change attributes
        countBook.get(position).setName(counterName);
        countBook.get(position).setInitValue(initialCounterValue);
        countBook.get(position).setCurrValue(currentCounterValue);
        countBook.get(position).setComment(counterComment);
        saveInFile();

        finish();
    }

    // onclick + button, increase currentValue and update the text box
    public void increaseCounter(View view) {
        countBook.get(position).increment();
        updateCounter();
    }

    // onclick - button, decrease currentValue and update the text box
    public void decreaseCounter(View view) {
        countBook.get(position).decrement();
        updateCounter();
    }

    // reset currentvalue to initialValue
    public void resetCounter(View view) {
        countBook.get(position).reset();
        updateCounter();
    }

    // remove counter from countBook array list and update save file
    // return to parent activity
    public void deleteCounter(View view) {
        countBook.remove(countBook.get(position));
        saveInFile();
        finish();
    }


}

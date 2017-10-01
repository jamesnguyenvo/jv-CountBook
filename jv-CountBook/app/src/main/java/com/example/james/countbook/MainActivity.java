package com.example.james.countbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private ArrayList<Counter> countBook;
    private ArrayAdapter<Counter> adapter;
    private ListView counterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        counterList = (ListView) findViewById(R.id.counterListView);

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();
        // create a list view for all counters
        adapter = new ArrayAdapter<Counter>(this, android.R.layout.simple_list_item_1, countBook);
        counterList.setAdapter(adapter);

        // allow for list in listview to be clicked
        // grabs position of counter
        // brings up counter details and allows for editing
        counterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Intent intent = new Intent((MainActivity)parent.getContext(), EditCounterActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
                adapter.notifyDataSetChanged();
        }
    });

    }

    // taken from lonelytwitter labs
    // used for app persistence, load countBook array list
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

    // onclick new counter
    // activity transfer to prompt user to create a counter
    public void initializeCounter(View view) {

        Intent intent = new Intent(this, InitializeCounterActivity.class);
        startActivity(intent);
    }

}

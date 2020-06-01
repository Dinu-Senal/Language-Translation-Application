package com.example.assessment_02_w1742312;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Comparator;

/*
 * Author : Dinu Senal Sendanayake
 * IIT Number : 2018445
 * UOW Number : W1742312
 * Activity : DisplayPhrases
 */

public class DisplayPhrases extends AppCompatActivity {

    private static final String TAG = "DatabaseLite";
    DatabaseLite mDatabaseLite;
    private ListView lv_displayPhrases;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_phrases);

        lv_displayPhrases = (ListView) findViewById(R.id.lv_phrasesList);
        mDatabaseLite = new DatabaseLite(this);

        populateListView();
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        // Getting the data and append into a list
        Cursor data = mDatabaseLite.getData();
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()){
            // Getting the data from the database in column 1
            // And add into a ArrayList
            listData.add(data.getString(1));
        }
        // Creating a list adapter and set adapter
        ListAdapter adapter = new ArrayAdapter<>(this, R.layout.custom_list_view_v1, listData);
        lv_displayPhrases.setAdapter(adapter);

        ((ArrayAdapter) adapter).sort(new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.compareToIgnoreCase(rhs);
            }
        });
    }
}

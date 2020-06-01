package com.example.assessment_02_w1742312;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;

/*
 * Author : Dinu Senal Sendanayake
 * IIT Number : 2018445
 * UOW Number : W1742312
 * Activity : DisplayEditPhrases
 */

public class DisplayEditPhrases extends AppCompatActivity {

    private static final String TAG = "DisplayEditPhrases";
    DatabaseLite mDatabaseLite;
    private ListView lv_displayEditablePhrases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_edit_phrases);

        lv_displayEditablePhrases = (ListView) findViewById(R.id.lv_editList);
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
        ListAdapter adapter = new ArrayAdapter<>(this, R.layout.custom_list_view_v2, listData);
        lv_displayEditablePhrases.setAdapter(adapter);

        ((ArrayAdapter) adapter).sort(new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.compareToIgnoreCase(rhs);
            }
        });
        //set an onItemClickListener to the ListView
        lv_displayEditablePhrases.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You Clicked on " + name);

                // Getting the id associated with that name
                Cursor data = mDatabaseLite.getItemID(name);
                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if(itemID > -1){
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
                    Intent editScreenIntent = new Intent(DisplayEditPhrases.this, EditPhrases.class);
                    editScreenIntent.putExtra("id",itemID);
                    editScreenIntent.putExtra("name",name);
                    startActivity(editScreenIntent);
                }
                else{
                    // If there are no phrase or word in database
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.custom_toast_v2, (ViewGroup)
                            findViewById(R.id.toast_wrong_message_layout));
                    TextView failure = layout.findViewById(R.id.tv_wrong_message);
                    failure.setText("No ID associated with that name");
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.BOTTOM, 0 , 0 );
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                }
            }
        });
    }
}

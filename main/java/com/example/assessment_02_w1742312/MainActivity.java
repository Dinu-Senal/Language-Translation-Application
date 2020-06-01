package com.example.assessment_02_w1742312;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/*
 * Author : Dinu Senal Sendanayake
 * IIT Number : 2018445
 * UOW Number : W1742312
 * Activity : Main Page
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 5 Button for user interactions
        Button addPhrases = findViewById(R.id.btn_add_phrases);
        Button displayPhrases = findViewById(R.id.btn_display_phrases);
        Button editPhrases = findViewById(R.id.btn_edit_phrases);
        Button languageSub = findViewById(R.id.btn_lan_sub);
        Button translate = findViewById(R.id.btn_translate);

        addPhrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddPhrases.class));
            }
        });

        displayPhrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DisplayPhrases.class));
            }
        });

        editPhrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DisplayEditPhrases.class));
            }
        });

        languageSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LanguageSubscription.class));
            }
        });

        translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TranslatePhrases.class));
            }
        });

    }
}

/*
 ** References
 * Android Studio : SQLite Data Adding and Deleting - Youtube
 * Android ListView Ep.09 : Custom Filter/Search - BaseAdapter [With Images and Text] - YouTube
 * Android Log.v(), Log.d(), Log.i(), Log.w(), Log.e() - When to use each one? - StackOverFlow
 * How to set an onclick listener for an imagebutton in an alertdialog - StackOverFlow
 * Log | Android Developers
 * Autosizing TextViews | Android Developers
 */

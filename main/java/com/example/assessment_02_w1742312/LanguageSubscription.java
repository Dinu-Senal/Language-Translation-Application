package com.example.assessment_02_w1742312;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/*
 * Author : Dinu Senal Sendanayake
 * IIT Number : 2018445
 * UOW Number : W1742312
 * Activity : Language Subscription
 */

public class LanguageSubscription extends AppCompatActivity {

    ArrayList<String> checkedLanguages = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_subscription);
    }

    public void checkedAfrikaans(View view) {
        checkedLanguages.add("Afrikaans");
    }

    public String[] languageArray() {
        return (String[]) checkedLanguages.clone();
    }

}

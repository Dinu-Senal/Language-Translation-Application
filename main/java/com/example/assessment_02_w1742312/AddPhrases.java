package com.example.assessment_02_w1742312;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/*
 * Author : Dinu Senal Sendanayake
 * IIT Number : 2018445
 * UOW Number : W1742312
 * Activity : AddPhrases
 */

public class AddPhrases extends AppCompatActivity {

    private static final String TAG = "AddPhrases";

    DatabaseLite mDataBaseLite;
    private Button btnAdd;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phrases);

        editText = findViewById(R.id.et_addPhrases);
        btnAdd = findViewById(R.id.btn_add_phrases);
        mDataBaseLite = new DatabaseLite(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = editText.getText().toString();
                if (editText.length() != 0) {
                    addData(newEntry);
                    editText.setText("");

                } else {
                    // Message when data hasn't inserted by the user
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.custom_toast_v2, (ViewGroup)
                            findViewById(R.id.toast_wrong_message_layout));
                    TextView failure = layout.findViewById(R.id.tv_wrong_message);
                    failure.setText("You Must Enter a Phrase or a Word");
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.BOTTOM, 0 , 0 );
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                }
            }
        });
    }

    public void addData(String newEntry) {
        boolean insertData = mDataBaseLite.addData(newEntry);

        if (insertData) {
            // Message when data insertion is successful
            showToast_01();
        } else {
            // Message when data insertion is a failure
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast_v2, (ViewGroup)
                          findViewById(R.id.toast_wrong_message_layout));
            TextView failure = layout.findViewById(R.id.tv_wrong_message);
            failure.setText("Something Went Wrong");
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.BOTTOM, 0 , 0 );
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        }
    }

    // Message when insertion is successful
    private void showToast_01() {
        LayoutInflater inflater =  getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_v1, (ViewGroup) findViewById(R.id.toast_correct_message_layout));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0 , 0 );
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}


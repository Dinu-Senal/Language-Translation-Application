package com.example.assessment_02_w1742312;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/*
 * Author : Dinu Senal Sendanayake
 * IIT Number : 2018445
 * UOW Number : W1742312
 * Activity : EditPhrases
 */

public class EditPhrases extends AppCompatActivity {

    private static final String TAG = "EditPhrases";
    DatabaseLite mDatabaseLite;
    private Button btnSave, btnDelete, btnDisplay;
    private ImageButton btnHome;
    private EditText editable_item;
    private String selectedName;
    private int selectedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_phrases);

        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        btnHome = findViewById(R.id.btn_home_v1);
        btnDisplay = findViewById(R.id.btnDisplay);
        editable_item = findViewById(R.id.editable_item);
        mDatabaseLite = new DatabaseLite(this);

        // Getting the intent extra from the DisplayEditPhrases
        Intent receivedIntent = getIntent();

        // Getting the itemID we passed as an extra
        selectedID = receivedIntent.getIntExtra("id", -1); // -1 is a default value

        // Getting the name we passed as an extra
        selectedName = receivedIntent.getStringExtra("name");

        // Setting the text to show the current selected name
        editable_item.setText(selectedName);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = editable_item.getText().toString();
                if (!item.equals("")) {
                    mDatabaseLite.updateName(item, selectedID, selectedName);
                    showToast_03();
                } else {
                    // Message when data didn't inserted by the user
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

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseLite.deleteName(selectedID, selectedName);
                editable_item.setText("");
                showToast_04();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditPhrases.this, MainActivity.class));
            }
        });

        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditPhrases.this, DisplayPhrases.class));
            }
        });

    }

    // Message when insertion is successful
    private void showToast_03() {
        LayoutInflater inflater =  getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_v3, (ViewGroup) findViewById(R.id.toast_update_message_layout));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0 , 0 );
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    // Message when insertion is successful
    private void showToast_04() {
        LayoutInflater inflater =  getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_v4, (ViewGroup) findViewById(R.id.toast_failure_message_layout));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0 , 0 );
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}

package com.example.assessment_02_w1742312;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.ibm.cloud.sdk.core.http.HttpMediaType;
import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.developer_cloud.android.library.audio.StreamPlayer;
import com.ibm.watson.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.language_translator.v3.LanguageTranslator;
import com.ibm.watson.language_translator.v3.model.TranslateOptions;
import com.ibm.watson.language_translator.v3.model.TranslationResult;
import com.ibm.watson.language_translator.v3.util.Language;
import com.ibm.watson.text_to_speech.v1.model.SynthesizeOptions;

public class TranslationDemo extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final String TAG = "TranslationDemo";
    DatabaseLite mDatabaseLite;
    private Button translate_btn;
    private ImageButton btnHome;
    public LanguageTranslator translationService;
    public TextView translatable_word;
    private String selectedName;
    private int selectedID;
    // For pronounce task
    private StreamPlayer player = new StreamPlayer();
    private TextToSpeech textService;

    String[] names = {"Spanish", "French",
            "Arabic", "Estonian", "Japanese"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation_demo);

        translatable_word = findViewById(R.id.translate_word);
        translate_btn = findViewById(R.id.btn_translate_v2);
        btnHome = findViewById(R.id.btn_home_v2);
        mDatabaseLite = new DatabaseLite(this);

        // Getting the intent extra from the TranslatePhrase
        Intent receivedIntent = getIntent();

        // Getting the itemID we passed as an extra
        selectedID = receivedIntent.getIntExtra("id", -1); // -1 is a default value

        // Getting the name we passed as an extra
        selectedName = receivedIntent.getStringExtra("name");

        // Setting the text to show the current selected name
        translatable_word.setText(selectedName);

        // Creating the spinner
        Spinner spinner = findViewById(R.id.languages_spinner);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }

        // Creating ArrayAdapter using the string array
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languages_spinner,
                android.R.layout.simple_spinner_item);

        // Specifying the layout when list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Applying the adapter to the spinner
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }

        translationService = initLanguageTranslatorService();
        textService = initTextToSpeechService();
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TranslationDemo.this, MainActivity.class));
            }
        });
    }

    // Implemented methods for spinner
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        final String spinner_name_equal = names[position];
        translate_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (spinner_name_equal == "Spanish") {
                    displayToast(names[0]);
                    new TranslationTask_01().execute(selectedName);
                }
                if (spinner_name_equal == "French") {
                    displayToast(names[1]);
                    new TranslationTask_02().execute(selectedName);
                }
                if (spinner_name_equal == "Arabic") {
                    displayToast(names[2]);
                    new TranslationTask_03().execute(selectedName);
                }
                if (spinner_name_equal == "Estonian") {
                    displayToast(names[3]);
                    new TranslationTask_04().execute(selectedName);
                }
                if (spinner_name_equal == "Japanese") {
                    displayToast(names[4]);
                    new TranslationTask_05().execute(selectedName);
                }
            }
        });
    }

    private LanguageTranslator initLanguageTranslatorService() {
        Authenticator authenticator = new IamAuthenticator("4W_I2bWeLTlHsnPdygdF6-0nwSw0gbaLFD3bElPWnIIv");
        LanguageTranslator service = new LanguageTranslator("2018-05-01", authenticator);

        service.setServiceUrl("https://api.eu-gb.language-translator.watson.cloud.ibm.com/instances/594cf162-be3a-477b-bb49-f5a9831bc5f4");
        return service;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
    
    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    private TextToSpeech initTextToSpeechService() {
        Authenticator authenticator = new
                IamAuthenticator("WKb_DU7Bvgvk9SkEQ16XiRdIM5eUQoJ6Ztk3Xq1QkHr_");
        com.ibm.watson.text_to_speech.v1.TextToSpeech service = new com.ibm.watson.text_to_speech.v1.TextToSpeech(authenticator);
        service.setServiceUrl("https://api.eu-gb.text-to-speech.watson.cloud.ibm.com/instances/c583edbc-b1c1-42a6-9d59-76d214965a24");
        return service;
    }

    private class SynthesisTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            SynthesizeOptions synthesizeOptions = new
                    SynthesizeOptions.Builder()
                    .text(params[0])
                    .voice(SynthesizeOptions.Voice.EN_US_LISAVOICE)
                    .accept(HttpMediaType.AUDIO_WAV)
                    .build();
            player.playStream(textService.synthesize(synthesizeOptions).execute()
                    .getResult());
            return "Did synthesize";
        }
    }

    public void pronounceClick(View view) {
        new SynthesisTask().execute("Selected english word pronounced like : " + selectedName);
    }

    private class TranslationTask_01 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            TranslateOptions translateOptions = new
                    TranslateOptions.Builder()
                    .addText(params[0])
                    .source(Language.ENGLISH)
                    .target("es")
                    .build();
            TranslationResult result
                    =
                    translationService.translate(translateOptions).execute().getResult();
            String firstTranslation =
                    result.getTranslations().get(0).getTranslation();
            return firstTranslation;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            translatable_word.setText(s);
        }
    }

    private class TranslationTask_02 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            TranslateOptions translateOptions = new
                    TranslateOptions.Builder()
                    .addText(params[0])
                    .source(Language.ENGLISH)
                    .target("fr")
                    .build();
            TranslationResult result
                    =
                    translationService.translate(translateOptions).execute().getResult();
            String firstTranslation =
                    result.getTranslations().get(0).getTranslation();
            return firstTranslation;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            translatable_word.setText(s);
        }
    }

    private class TranslationTask_03 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            TranslateOptions translateOptions = new
                    TranslateOptions.Builder()
                    .addText(params[0])
                    .source(Language.ENGLISH)
                    .target("ar")
                    .build();
            TranslationResult result
                    =
                    translationService.translate(translateOptions).execute().getResult();
            String firstTranslation =
                    result.getTranslations().get(0).getTranslation();
            return firstTranslation;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            translatable_word.setText(s);
        }
    }

    private class TranslationTask_04 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            TranslateOptions translateOptions = new
                    TranslateOptions.Builder()
                    .addText(params[0])
                    .source(Language.ENGLISH)
                    .target("et")
                    .build();
            TranslationResult result
                    =
                    translationService.translate(translateOptions).execute().getResult();
            String firstTranslation =
                    result.getTranslations().get(0).getTranslation();
            return firstTranslation;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            translatable_word.setText(s);
        }
    }

    private class TranslationTask_05 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            TranslateOptions translateOptions = new
                    TranslateOptions.Builder()
                    .addText(params[0])
                    .source(Language.ENGLISH)
                    .target("ja")
                    .build();
            TranslationResult result
                    =
                    translationService.translate(translateOptions).execute().getResult();
            String firstTranslation =
                    result.getTranslations().get(0).getTranslation();
            return firstTranslation;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            translatable_word.setText(s);
        }
    }
}

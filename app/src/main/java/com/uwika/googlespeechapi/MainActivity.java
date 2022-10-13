package com.uwika.googlespeechapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    TextToSpeech tts;
    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = findViewById(R.id.txtInput);
        tts = new TextToSpeech(this, this);
        tts.setSpeechRate((float) 1.2);
        tts.setPitch((float) 1.8);
        tts.setLanguage(Locale.CHINA);
    }

    public void speak(View v){
        String text = input.getText().toString();
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void dengerin (View v)
    {
        Intent i  = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en US");
        try {
            input.setText("");
            startActivityForResult(i, 1);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void simpan(View v){
        try {
            OutputStreamWriter o = new OutputStreamWriter(openFileOutput("output.txt", 0));
            o.write(input.getText().toString());
            o.close();

            Toast.makeText(this, "Data Sudah Tersimpan", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void load(View v){
        String temp;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStreamReader is = new InputStreamReader(openFileInput("output.txt"));
            BufferedReader br = new BufferedReader(is);
            while((temp=br.readLine()) != null){
                stringBuilder.append(temp + "\n");
            }
            is.close();
            input.setText(stringBuilder.toString());
            Toast.makeText(this, "Data berhasil diload", Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int RequestCode, int ResultCode, Intent data) {

        super.onActivityResult(ResultCode, RequestCode, data);
       switch (RequestCode)
        {
            case 1: {
                if (ResultCode == RESULT_OK && null != data) {
                    ArrayList<String> aa = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    input.setText(aa.get(0));
                }
                break;
            }
        }
    }

    @Override
    public void onInit(int i) {

    }
}
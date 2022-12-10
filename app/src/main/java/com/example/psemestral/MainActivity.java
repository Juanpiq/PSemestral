package com.example.psemestral;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String grabar;
    Intent intent1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //grabar = findViewById(R.id.texto);
        intent1 = new Intent(getApplicationContext(), Pasos.class);
    }
    private static final int RECOGNIZE_SPEECH_ACTIVITY = 1;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RECOGNIZE_SPEECH_ACTIVITY:
                if (resultCode == RESULT_OK && null != data){
                    ArrayList<String> speech = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String strSpeech2Text = speech.get(0);
                    grabar = strSpeech2Text;
                    if(grabar.equals("contador de pasos"))
                        Toast.makeText(this, "Contador de Pasos", Toast.LENGTH_SHORT).show();
                    else Toast.makeText(this, "Sensor no definido", Toast.LENGTH_SHORT).show();;
                }
                break;
            default:
                break;
        }
    }
    public void Hablar(View view){
        Intent intentActionRecognizeSpeech = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intentActionRecognizeSpeech.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,"es-MX");
        try{
            startActivityForResult(intentActionRecognizeSpeech, RECOGNIZE_SPEECH_ACTIVITY);
        } catch (ActivityNotFoundException a){
            Toast.makeText(this, "Tu dispositivo no soporta el reconocimiento por voz", Toast.LENGTH_SHORT).show();
        }
    }

    public void Activar (View view){
        RadioButton rb1CP = findViewById(R.id.rb1CP);
        RadioButton rb2Px = findViewById(R.id.rb2Px);
        RadioButton rb3 = findViewById(R.id.rb3);

        if(rb1CP.isChecked() == true){
            Toast.makeText(this, "Contador de Pasos", Toast.LENGTH_SHORT).show();
            startActivity(intent1);
        }
        else if(rb2Px.isChecked() == true){

        }
        else {

        }
    }
}
package com.example.psemestral;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Proximidad extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximitySensorListener;
    private EditText etTelefono;
    private TextView tvTelefono1, tvTelefono2, tvtitulo;
    private ImageButton ibTelefonoLlamar, ibTelefonoColgar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximidad);

        etTelefono = findViewById(R.id.etTelefono);
        tvTelefono1 = findViewById(R.id.tvTelefono1);
        tvTelefono2 = findViewById(R.id.tvTelefono2);
        tvtitulo = findViewById(R.id.tvtitulo);
        ibTelefonoLlamar = findViewById(R.id.ibTelefonoLlamar);
        ibTelefonoColgar = findViewById(R.id.ibTelefonoColgar);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        // Verificar si el sensor de proximidad está disponible
        if(proximitySensor == null){
            Toast.makeText(this, "El sensor de proximidad no está disponible", Toast.LENGTH_LONG).show();
            finish();
        }

        ibTelefonoLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numero = etTelefono.getText().toString();
                if (numero.trim().length() > 0) {
                    tvTelefono1.setText("Llamando...");

                    tvTelefono2.setVisibility(View.VISIBLE);
                    tvTelefono2.setText(numero.trim());

                    ibTelefonoLlamar.setVisibility(View.INVISIBLE);
                    ibTelefonoColgar.setVisibility(View.VISIBLE);

                    etTelefono.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    etTelefono.getText().clear();
                    etTelefono.setVisibility(View.INVISIBLE);

                    //Para ocultar el teclado al llamar
                    getCurrentFocus();
                    if(view != null){
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

                    proximitySensorListener = new SensorEventListener() {
                        @Override
                        public void onSensorChanged(SensorEvent sensorEvent) {
                            if(sensorEvent.values[0] < proximitySensor.getMaximumRange()){
                                getWindow().getDecorView().setBackgroundColor(Color.BLACK);
                                tvtitulo.setVisibility(View.INVISIBLE);
                                tvTelefono1.setVisibility(View.INVISIBLE);
                                tvTelefono2.setVisibility(View.INVISIBLE);
                                ibTelefonoColgar.setVisibility(View.INVISIBLE);
                            }
                            else{
                                getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                                tvtitulo.setVisibility(View.VISIBLE);
                                tvTelefono1.setVisibility(View.VISIBLE);
                                tvTelefono2.setVisibility(View.VISIBLE);
                                ibTelefonoColgar.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onAccuracyChanged(Sensor sensor, int i) {

                        }
                    };

                    sensorManager.registerListener(proximitySensorListener, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);

                } else {
                    Toast.makeText(Proximidad.this, "Ingrese Número de Teléfono", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ibTelefonoColgar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvTelefono1.setText("Teléfono");

                tvtitulo.setVisibility(View.VISIBLE);
                tvTelefono2.setVisibility(View.INVISIBLE);
                etTelefono.setVisibility(View.VISIBLE);
                ibTelefonoColgar.setVisibility(View.INVISIBLE);
                ibTelefonoLlamar.setVisibility(View.VISIBLE);

                sensorManager.unregisterListener(proximitySensorListener);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(proximitySensorListener);
    }
}
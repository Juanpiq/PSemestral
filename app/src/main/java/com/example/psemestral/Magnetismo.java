package com.example.psemestral;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Magnetismo extends AppCompatActivity implements SensorEventListener {

    TextView textView, textView3;

    private static SensorManager sensorManager;
    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnetismo);

        textView=findViewById(R.id.textView);
        textView3=findViewById(R.id.textView3);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (sensor != null){
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else{
            Toast.makeText(this, "No lo soporta", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float azimuth = Math.round(sensorEvent.values[0]);
        float pitch = Math.round(sensorEvent.values[1]);
        float roll = Math.round(sensorEvent.values[2]);

        double tesla = Math.sqrt((azimuth*azimuth) + (pitch*pitch) + (roll*roll));
        double vamo = tesla;

        String text = String.format("%.0f", tesla);
        textView.setText(text + "T");

        if (vamo >= 0 && vamo < 300){
            textView3.setText("El Campo Magnético es normal, es seguro");
        }
        else if (vamo < 0){
            textView3.setText("");
        }
        else{
            textView3.setText("El Campo Magnetico es peligroso, puedes desarrollar cáncer");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i){

    }
}
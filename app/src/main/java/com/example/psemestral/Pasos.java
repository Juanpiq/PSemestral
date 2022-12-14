package com.example.psemestral;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.hardware.Sensor;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.WindowManager;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;


public class Pasos extends AppCompatActivity implements SensorEventListener {
    private TextView textViewStepCounter;
    private TextView textviewStepDetector;
    public TextView tv1, tv2;
    private SensorManager sensorManager;
    private Sensor mStepCounter;
    private boolean isCounterSensorPresent;
    private ProgressBar pb;
    int stepCount=0;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasos);

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){ //ask for permission

            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);

        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        textViewStepCounter = findViewById(R.id.textViewStepCounter);
        textviewStepDetector = findViewById(R.id.textviewStepDetector);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null){

            mStepCounter=sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isCounterSensorPresent=true;
        }
        else {
            textViewStepCounter.setText("El sensor del contador no está presente");
            isCounterSensorPresent=false;
        }
    pb=findViewById(R.id.pb);



//----- fin del main
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent){
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        if(sensorEvent.sensor==mStepCounter){
            stepCount=(int) sensorEvent.values[0];
            textViewStepCounter.setText (String.valueOf(stepCount));
            pb.setMax(8000);
            pb.setProgress(stepCount);
            //tv1.setText("Meta");
            tv1.setText("Meta: " + (stepCount) +"/ 8000");
            tv2.setText("Calorias quemadas: " + String.format("%.02f",stepCount*0.04));
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i){

    }
    @Override
    public void onResume(){
        super.onResume();
        if (sensorManager.getDefaultSensor (Sensor.TYPE_STEP_COUNTER) !=null)
            sensorManager.registerListener(this, mStepCounter, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    public void onPause(){
        super.onPause();

        if (sensorManager.getDefaultSensor (Sensor.TYPE_STEP_COUNTER)!=null)
            sensorManager.unregisterListener(this, mStepCounter);

    }

}
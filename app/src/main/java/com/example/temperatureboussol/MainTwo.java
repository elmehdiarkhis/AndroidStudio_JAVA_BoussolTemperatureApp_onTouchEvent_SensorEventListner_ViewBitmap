package com.example.temperatureboussol;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainTwo extends AppCompatActivity {

    //======
    public static SensorManager mySensorManager; //(mon intermediaire)
    public static Sensor mySensor;

    public static Sensor mySensorMagnetude;
    public static Sensor mySensorAccelerometre;
    //===================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //====
        mySensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        if(GlobalVar.btnTempClicked==true){
            mySensor = mySensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        }else if(GlobalVar.btnBoussolClicked==true){
            mySensorMagnetude = mySensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            mySensorAccelerometre = mySensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        }
        //==============================================

        SendToTwo sendtotwo = new SendToTwo(this);
        setContentView(sendtotwo);

    }
}

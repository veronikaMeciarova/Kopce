package com.example.vm.kopce;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

public class Compass implements SensorEventListener {

    private SensorManager sensorManager;
    private Context context;
    private TextView text;

    public Compass(Context context, TextView text) {
        this.context = context;
        this.text = text;
        sensorManager = (SensorManager)context.getSystemService(context.SENSOR_SERVICE);
        Sensor compass = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(this, compass, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float x=Math.round(event.values[0]);
        float y=Math.round(event.values[1]);
        float z=Math.round(event.values[2]);
        double degree = (Math.atan2(x,y) * 180) / Math.PI;
        if (degree < 0) {
            degree = degree + 360;
        }
        int degreeInt = (int)degree;
        text.setText(" x: " + Float.toString(x) + "\n y: " + Float.toString(y) + "\n z: " + Float.toString(z) + "\n" + svetovaStrana(degree) + "   " + Integer.toString(degreeInt));
    }

    public String svetovaStrana (double degree){
        if ((degree <= 45 && degree >= 0) || (degree <= 360 && degree > 270)) {
            return "S";
        }
        if (degree <= 270 && degree > 225) {
            return "V";
        }
        if (degree <= 225 && degree > 135) {
            return "J";
        }
        if (degree <= 135 && degree > 45) {
            return "Z";
        }
        return "Neviem zistiť svetovú stranu.";
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}


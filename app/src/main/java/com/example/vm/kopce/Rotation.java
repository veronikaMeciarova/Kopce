package com.example.vm.kopce;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

public class Rotation implements SensorEventListener {

    private SensorManager sensorManager;
    private Context context;
    private TextView text;
    private float[] mAccelerometer;
    private float[] mGeomagnetic;

    public Rotation(Context context, TextView text) {
        this.context = context;
        this.text = text;
        sensorManager = (SensorManager)context.getSystemService(context.SENSOR_SERVICE);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor geomagnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mAccelerometer = new float[3];
        mGeomagnetic = new float[3];
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, geomagnetic, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                for (int i=0; i<event.values.length; i++) {
                    mAccelerometer[i] = event.values[i];
                }
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                for (int i=0; i<event.values.length; i++) {
                    mGeomagnetic[i] = event.values[i];
                }
                break;
            default:
                return;
        }
        this.orientation();
    }

    public void orientation () {
        float R[] = new float[9];

        sensorManager.getRotationMatrix(R, null, mAccelerometer, mGeomagnetic);
        float orientation[] = new float[3];
        sensorManager.getOrientation(R, orientation);
        int x = (int) ((orientation[0] * 180) / Math.PI);
        int y = (int) ((orientation[1] * 180) / Math.PI);
        int z = (int) ((orientation[2] * 180) / Math.PI);
        
        text.setText(" x: " + Integer.toString(x) + "\n y: " + Integer.toString(y) + "\n z: " + Integer.toString(z));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}


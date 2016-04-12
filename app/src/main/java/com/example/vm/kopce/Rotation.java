package com.example.vm.kopce;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

import java.util.ArrayList;

public class Rotation implements SensorEventListener {

    private SensorManager sensorManager;
    private Context context;
    private TextView text;
    private float[] mAccelerometer;
    private float[] mGeomagnetic;
    public Data data;
    public TextView tvPeaks;
    private ArrayList vrcholy;

    public Rotation(Context context, TextView text, Data data, TextView tvPeaks, ArrayList vrcholy) {
        this.context = context;
        this.text = text;
        this.data = data;
        this.tvPeaks = tvPeaks;
        this.vrcholy = vrcholy;
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

//        values[0]: azimuth, rotation around the -Z axis, i.e. the opposite direction of Z axis.
//        values[1]: pitch, rotation around the -X axis, i.e the opposite direction of X axis.
//        values[2]: roll, rotation around the Y axis.

        sensorManager.getOrientation(R, orientation);
        int x = (int) ((orientation[0] * 180) / Math.PI);
        int y = (int) ((orientation[1] * 180) / Math.PI);
        int z = (int) ((orientation[2] * 180) / Math.PI);

        ArrayList<Kopec> vrcholyVSmere = data.vrcholyVSmere(vrcholy,  49.182103, 18.764652, -x, 90);
        String viditelneVrcholyString = data.vrcholyToString(vrcholyVSmere);
        tvPeaks.setText(viditelneVrcholyString);

        text.setText(" x: " + Integer.toString(x) + "\n y: " + Integer.toString(y) + "\n z: " + Integer.toString(z) + "\n" + svetovaStrana(-x));

    }

    public String svetovaStrana (double degree){
        if (degree < 0) {
            degree = degree + 360;
        }

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


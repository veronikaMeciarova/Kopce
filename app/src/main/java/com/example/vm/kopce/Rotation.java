package com.example.vm.kopce;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Rotation implements SensorEventListener {

    private SensorManager sensorManager;
    private Context context;
    private float[] mAccelerometer;
    private float[] mGeomagnetic;
    private int x = 0;
    private int y = 0;
    private int z = 0;

    public Rotation(Context context) {
        this.context = context;
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
        x = (int) ((orientation[0] * 180) / Math.PI);
        y = (int) ((orientation[1] * 180) / Math.PI);
        z = (int) ((orientation[2] * 180) / Math.PI);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int smerKamery() {
        int degree = -x;
        if (degree < 0) {
            degree = degree + 360;
        }
        degree = (degree+270)%360;

        return degree;
    }

    public String svetovaStrana (double degree){
        if (degree < 0) {
            degree = degree + 360;
        }

        if ((degree <= 45 && degree >= 0) || (degree <= 360 && degree > 315)) {
            return "S";
        }
        if (degree <= 315 && degree > 225) {
            return "V";
        }
        if (degree <= 225 && degree > 135) {
            return "J";
        }
        if (degree <= 135 && degree > 45) {
            return "Z";
        }
        return "Neviem zistiť svetovú stranu.  " + degree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


}


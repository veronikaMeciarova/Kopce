package com.example.vm.kopce;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;

public class GPSTracker extends Service implements LocationListener{

    private final Context context;

    boolean isGPSEnabled = false;
    double nezmysel = 20000;

    private LocationManager locationManager;

    public GPSTracker (Context context){
        this.context = context;
        getLocation();
    }

    public Location getLocation() {
        Location location = null;
        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            if (canGetLocation()) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return location;
    }

    public double getLatitude() {
        Location loc = this.getLocation();
        if (loc == null) {
            return nezmysel;
        } else {
            return loc.getLatitude();
        }
    }

    public double getLongitude() {
        Location loc = this.getLocation();
        if (loc == null) {
            return nezmysel;
        } else {
            return loc.getLongitude();
        }
    }

    public double getAltitude() {
        Location loc = this.getLocation();
        if (loc == null) {
            return 20000;
        } else {
            return loc.getAltitude();
        }
    }

    public boolean canGetLocation() {
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isGPSEnabled;
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("GPS is settings");
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

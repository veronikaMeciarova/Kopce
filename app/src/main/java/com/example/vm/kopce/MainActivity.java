package com.example.vm.kopce;

        import android.hardware.SensorManager;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import android.content.Context;
        import android.location.Location;
        import android.location.LocationListener;
        import android.location.LocationManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tvGPS = (TextView)findViewById(R.id.textViewGPS);
        Button btnGPS = (Button)findViewById(R.id.buttonGPS);
        final TextView tvAltitude = (TextView)findViewById(R.id.textViewAltitude);
        final GPSTracker gps = new GPSTracker(MainActivity.this); //singleton? vzdy treba novu instanciu
        btnGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gps.canGetLocation()) {
                    double lat = gps.getLatitude();
                    double lon = gps.getLongitude();
                    double altit = gps.getAltitude();
                    if (lat != 200 && lon != 200) {
                        tvGPS.setText("Súradnice GPS: \n" + lat + " \n" + lon);
                    } else {
                        tvGPS.setText("Súradnice nie je možné získať - skúste, prosím, neskôr");
                    }
                    if (altit != 20000) {
                        tvAltitude.setText("Nadmorská výška: \n" + altit);
                    } else {
                        tvAltitude.setText("Nie je možné zistiť nadmorskú výšku - skúste, prosím, neskôr.");
                    }
                } else {
                    gps.showSettingsAlert();
                }
/*
                MySensors sensors = new MySensors();
                    tvCompass.setText("Compass: " + Float.toString(sensors.getCompass())); */
            }
        });


    }
}

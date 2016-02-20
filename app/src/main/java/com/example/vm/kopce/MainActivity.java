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

        import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    double nezmysel = 20000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Data data = new Data();
        final TextView tvGPS = (TextView)findViewById(R.id.textViewGPS);
        Button btnGPS = (Button)findViewById(R.id.buttonGPS);
        final TextView tvAltitude = (TextView)findViewById(R.id.textViewAltitude);
        final TextView tvCompass = (TextView)findViewById(R.id.textViewCompass);
        final TextView tvRotation = (TextView)findViewById(R.id.textViewRotation);
        final TextView tvPeaks = (TextView)findViewById(R.id.textPeaks);
        final GPSTracker gps = new GPSTracker(MainActivity.this);
        btnGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gps.canGetLocation()) {
                    double lat = gps.getLatitude();
                    double lon = gps.getLongitude();
                    double altit = gps.getAltitude();
                    if (lat != nezmysel && lon != nezmysel) {
                        tvGPS.setText("Súradnice GPS: \n" + lat + " \n" + lon);
                    } else {
                        tvGPS.setText("Súradnice nie je možné získať - skúste, prosím, neskôr");
                    }
                    if (altit != nezmysel) {
                        tvAltitude.setText("Nadmorská výška: \n" + altit);
                    } else {
                        tvAltitude.setText("Nie je možné zistiť nadmorskú výšku - skúste, prosím, neskôr.");
                    }
                    final ArrayList<Kopec> viditelneVrcholy = data.najblizsieVrcholy(lon,lat,0.1);
                    final String viditelneVrcholyString = data.vrcholyToString(viditelneVrcholy);
                    tvPeaks.setText(viditelneVrcholyString);
                } else {
                    gps.showSettingsAlert();
                }

                Compass compass = new Compass (MainActivity.this, tvCompass);
                Rotation rotation = new Rotation (MainActivity.this, tvRotation);
            }
        });


    }
}

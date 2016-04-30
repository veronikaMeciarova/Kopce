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
        final Data data = new Data(); // mal by byť Singleton!!
        final GPSTracker gps = new GPSTracker(MainActivity.this);

        Button btnGPS = (Button)findViewById(R.id.buttonGPS);
        Button btnExit = (Button)findViewById(R.id.buttonExit);

        final TextView tvGPS = (TextView)findViewById(R.id.textViewGPS);
        final TextView tvAltitude = (TextView)findViewById(R.id.textViewAltitude);
        final TextView tvRotation = (TextView)findViewById(R.id.textViewRotation);
        final TextView tvPeaks = (TextView)findViewById(R.id.textPeaks);

        final Rotation rotation = new Rotation (MainActivity.this);

        btnGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gps.canGetLocation()) {
                    double lat = gps.getLatitude();
                    double lon = gps.getLongitude();
                    double altit = gps.getAltitude();

                    //LEN PRE TESTOVANIE!!!
                    altit = 400;

                    //Rosina
//                    lon = 18.764652;
//                    lat = 49.182103;

                    //Matfyz
                    lat = 48.150959;
                    lon = 17.070030;

                    // GPS
                    if (lat != nezmysel && lon != nezmysel) {
                        tvGPS.setText("Súradnice GPS: \n" + lat + " \n" + lon);
                    } else {
                        tvGPS.setText("Súradnice nie je možné získať - skúste, prosím, neskôr");
                    }

                    // NADMORSKA VYSKA
                    if (altit != nezmysel) {
                        tvAltitude.setText("Nadmorská výška: \n" + altit);
                    } else {
                        tvAltitude.setText("Nie je možné zistiť nadmorskú výšku - skúste, prosím, neskôr.");
                    }

                    //ROTACIA
                    int x = rotation.getX();
                    int y = rotation.getY();
                    int z = rotation.getZ();
                    //int smerKamery = rotation.smerKamery();
                    tvRotation.setText(" x: " + Integer.toString(x) + "\n y: " + Integer.toString(y) + "\n z: " + Integer.toString(z) + "\n" + rotation.svetovaStrana(-x));


                    //VRCHOLY NA DOHLAD
                    int viditelnostVKm = 10;
                    int rozptyl = 40; // v stupnoch, sucet do prava aj do lava
                    ArrayList<Kopec> viditelneVrcholy = data.vrcholyVOkruhu(lon, lat, viditelnostVKm);
                    ArrayList<Kopec> vrcholyVSmere = data.vrcholyVSmere(viditelneVrcholy, lat, lon, -x, rozptyl);
                    String viditelneVrcholyString = data.vrcholyToString(vrcholyVSmere);
                    tvPeaks.setText("Vrcholy do " + viditelnostVKm + "km:\n" + viditelneVrcholyString);

                } else {
                    gps.showSettingsAlert();
                }
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });


    }
}

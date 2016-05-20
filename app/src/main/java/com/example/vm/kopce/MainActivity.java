package com.example.vm.kopce;

        import android.content.Context;
        import android.content.pm.ActivityInfo;
        import android.hardware.Camera;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.DisplayMetrics;
        import android.view.SurfaceHolder;
        import android.view.SurfaceView;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.ViewParent;
        import android.view.Window;
        import android.view.WindowManager;
        import android.widget.Button;
        import android.widget.FrameLayout;
        import android.widget.LinearLayout;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    double nezmysel = 20000;

    CameraPreview cv;
    DrawView dv;
    FrameLayout alParent;
    Button btnExit;
    Button btnGPS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Data data = new Data(MainActivity.this); // mal by byť Singleton!!
        final GPSTracker gps = new GPSTracker(MainActivity.this);

        btnGPS = (Button)findViewById(R.id.buttonGPS);
        btnExit = (Button)findViewById(R.id.buttonExit);


        final TextView tvGPS = (TextView)findViewById(R.id.textViewGPS);
        final TextView tvAltitude = (TextView)findViewById(R.id.textViewAltitude);
        final TextView tvRotation = (TextView)findViewById(R.id.textViewRotation);
        final TextView tvPeaks = (TextView)findViewById(R.id.textPeaks);

        final Rotation rotation = new Rotation (MainActivity.this);

        // Make this activity, full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        btnGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gps.canGetLocation()) {
                    double lat = gps.getLatitude();
                    double lon = gps.getLongitude();
                    double altit = gps.getAltitude();

                    //LEN PRE TESTOVANIE!!!
//
//                    //Rosina
//                    altit = 400;
//                    lon = 18.764652;
//                    lat = 49.182103;

//                    //Matfyz
//                    altit = 140;
//                    lat = 48.150959;
//                    lon = 17.070030;

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
                    int smerKamery = rotation.smerKamery();
                    tvRotation.setText(" x: " + Integer.toString(x) + "\n y: " + Integer.toString(y) + "\n z: " + Integer.toString(z) + "\n" + rotation.svetovaStrana(smerKamery));


                    //VRCHOLY NA DOHLAD
                    int viditelnostVKm = 8;
                    int rozptyl = 90; // v stupnoch, sucet doprava aj dolava
                    ArrayList<Kopec> viditelneVrcholy = data.vrcholyVOkruhu(lon, lat, viditelnostVKm);
                    ArrayList<Kopec> vrcholyVSmere = data.vrcholyVRozptyle(viditelneVrcholy, lat, lon, smerKamery, rozptyl);
                    PolohaMobilu polohaMobilu = new PolohaMobilu(lat, lon, altit, rotation);
                    ArrayList<KartezianskyKopec> kartezianskeKopce =  data.KopceDoKartezianskej(vrcholyVSmere,polohaMobilu);

                    dv = new DrawView(MainActivity.this);
                    dv.setKopce(kartezianskeKopce);
                    alParent.addView(dv);
                    btnGPS.bringToFront();
                    btnExit.bringToFront();

                    String viditelneVrcholyString = data.vrcholyToString(vrcholyVSmere, polohaMobilu);
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

    @Override
    protected void onPause() {
        super.onPause();
        if (cv != null){
            cv.onPause();
            cv = null;
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        Load();
    }

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return c;
    }

    public void Load(){
        Camera c = getCameraInstance();
        if (c != null){
            alParent = (FrameLayout)findViewById(R.id.frameLayout);

            alParent.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.FILL_PARENT));

            cv = new CameraPreview(this,c);
            alParent.addView(cv);

            setContentView(alParent);
            btnExit.bringToFront();
            btnGPS.bringToFront();
        }
        else { // ak nenasiel kameru
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Unable to find camera. Closing.", Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }
    }
}

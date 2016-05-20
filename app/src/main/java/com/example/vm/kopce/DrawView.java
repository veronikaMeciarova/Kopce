package com.example.vm.kopce;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.ArrayList;

public class DrawView extends SurfaceView{
    private Paint textPaint = new Paint();
    private Paint paint = new Paint();
    private ArrayList<KartezianskyKopec> kopce = new ArrayList<>();
    private Canvas canvas;
    private Context context;
    private int deviceWidth;
    private int deviceHeight;

    public DrawView(Context context) {
        super(context);
        this.context = context;
        // Create out paint to use for drawing
        textPaint.setARGB(255, 228, 68, 68);
        textPaint.setTextSize(30);
        // This call is necessary, or else the
        // draw method will not be called.
        setWillNotDraw(false);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        this.deviceWidth = displayMetrics.widthPixels;
        this.deviceHeight = displayMetrics.heightPixels;
    }

    @Override
    protected void onDraw(Canvas canvas){
        int xShift = deviceWidth/2;
        int yShift = deviceHeight/2;
        for (KartezianskyKopec k : kopce) {
            canvas.drawText(k.getNazov(), k.getX()+xShift-20, yShift-k.getY()-20, textPaint);
            canvas.drawCircle(k.getX()+xShift, yShift-k.getY(), 3, textPaint);
        }
    }

    public void setKopce (ArrayList<KartezianskyKopec> kopce) {
        this.kopce = kopce;
    }


}
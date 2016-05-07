package com.example.vm.kopce;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

import java.util.ArrayList;

public class DrawView extends SurfaceView{
    private Paint textPaint = new Paint();
    private Paint paint = new Paint();
    private ArrayList<KartezianskyKopec> kopce = new ArrayList<>();
    private Canvas canvas;

    public DrawView(Context context) {
        super(context);
        // Create out paint to use for drawing
        textPaint.setARGB(255, 200, 0, 0);
        textPaint.setTextSize(20);
        // This call is necessary, or else the
        // draw method will not be called.
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas){
//        this.canvas = canvas;
        for (KartezianskyKopec k : kopce) {
            canvas.drawText(k.getNazov(), k.getX()+380, k.getY()+220, textPaint);
            canvas.drawCircle(k.getX()+400, k.getY()+240, 3, textPaint);
        }
    }

    public void setKopce (ArrayList<KartezianskyKopec> kopce) {
        this.kopce = kopce;
    }
}
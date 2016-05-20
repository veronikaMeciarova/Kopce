package com.example.vm.kopce;

import java.util.ArrayList;

/**
 * Created by VM on 3. 5. 2016.
 */
public class KartezianskyKopec {

    private int x2d;
    private int y2d;
    public double x3d;
    public double y3d;
    public double z3d;
    private String nazov;

    /**
     * @param x suradnica x kopca v 3D kartezianskej suradnicovej sustave
     * @param y suradnica x kopca v 3D kartezianskej suradnicovej sustave
     * @param z suradnica x kopca v 3D kartezianskej suradnicovej sustave
     * @param zoom vzdialenost pozorovatela od obrazovky
     * */
    public KartezianskyKopec(String nazov, double x, double y, double z, double zoom) {
//        this.x2d = (int)((x/(-zoom-z))*(-zoom));
//        this.y2d = (int)((y/(-zoom-z))*(-zoom));
        this.x3d = x;
        this.y3d = y;
        this.z3d = z;
        this.x2d = (int) ((x/z)*zoom);
        this.y2d = (int) ((y/z)*zoom);
        this.nazov = nazov;
    }

    public int getX() {
        return x2d;
    }

    public int getY() {
        return y2d;
    }

    public String getNazov() {
        return nazov;
    }
}

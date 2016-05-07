package com.example.vm.kopce;

import java.util.ArrayList;

/**
 * Created by VM on 3. 5. 2016.
 */
public class KartezianskyKopec {

    private int x;
    private int y;
    private String nazov;

    /**
     * @param x suradnica x kopca v 3D kartezianskej suradnicovej sustave
     * @param y suradnica x kopca v 3D kartezianskej suradnicovej sustave
     * @param z suradnica x kopca v 3D kartezianskej suradnicovej sustave
     * @param zoom vzdialenost pozorovatela od obrazovky
     * */
    public KartezianskyKopec(String nazov, double x, double y, double z, double zoom) {
        this.x = (int)((x/(-zoom-z))*(-zoom));
        this.y = (int)((y/(-zoom-z))*(-zoom));
//        this.x = (int) ((x/z)*zoom);
//        this.y = (int) ((y/z)*zoom);
        this.nazov = nazov;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getNazov() {
        return nazov;
    }
}

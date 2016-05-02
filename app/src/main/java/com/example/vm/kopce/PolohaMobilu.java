package com.example.vm.kopce;

public class PolohaMobilu {
    double latitude;
    double longtitude;
    double altitude;
    double x;
    double y;
    double z;
    double smerKamery;

    public PolohaMobilu (double lat, double lon, double alt, Rotation rot) {
        this.latitude = lat;
        this.longtitude = lon;
        this.altitude = alt;
        this.x = rot.getX();
        this.y = rot.getY();
        this.z = rot.getZ();
        this.smerKamery = rot.smerKamery();
    }
}

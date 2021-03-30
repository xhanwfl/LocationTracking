package com.example.zenly2;

import java.util.HashMap;
import java.util.Map;

public class UserLocation {
    private double latitude;
    private double longitude;


    public UserLocation(){    }
    public UserLocation(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public double getLatitude(){
        return latitude;
    }
    public double getLongitude(){
        return longitude;
    }



}

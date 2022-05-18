package com.example.springprojectdemo.dataobject;

import lombok.Data;

import java.io.Serializable;

@Data
public class Location implements Serializable {

    double latitude;

    double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}

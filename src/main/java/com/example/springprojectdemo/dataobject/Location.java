package com.example.springprojectdemo.dataobject;

import lombok.Data;

import java.io.Serializable;

@Data
public class Location implements Serializable {

    double latitude;

    double longitude;
}

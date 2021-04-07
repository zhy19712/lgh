package com.example.demo.entity;

import lombok.Data;

@Data
public class Tank {
    private int id;
    private String name;
    private String waterIn;
    private String waterOut;
    private String waterGo;
    private String callDry;
    private String callSmallrain;
    private String callBigrain;
    private String callAfterrain;
    private String location;
    private String district;
    private double standard;
    private double level;
    private double heightBottom;
    private double heightRoof;
    private double area;
    private String builder;
    private String manager;
    private String time;
    private String run;
    private double lon;
    private double lat;
}

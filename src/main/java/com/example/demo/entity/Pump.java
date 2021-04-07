package com.example.demo.entity;

import lombok.Data;

@Data
public class Pump {
    private int id;
    private String name;
    private String startLevel;
    private String stopLevel;
    private String stage;
    private double designedFlow;
    private String type;
    private String designedK;
    private int num;
    private double drain;
    private double kw;
    private String time;
    private double area;
    private String power;
    private double diameter;
    private String flowD;
    private String location;
    private String river;
    private String station;
    private String qualityStation;
    private double lon;
    private double lat;
    private String manager;
    private String builder;
    private String designer;
    private int status;
    private String maintainer;
}

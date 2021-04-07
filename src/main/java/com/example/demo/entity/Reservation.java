package com.example.demo.entity;

import lombok.Data;

@Data
public class Reservation {
    private int id;
    private String name;
    private String location;
    private double checkedLevel;
    private double designedLevel;
    private double normalLevel;
    private double floodLevel;
    private double basinArea;
    private String model;
    private double length;
    private double height;
    private double area;
    private String manager;
    private String builder;
    private String time;
}

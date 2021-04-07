package com.example.demo.entity;

import lombok.Data;

@Data
public class Box {
    private int id;
    private String name;
    private double startLon;
    private double endLon;
    private double startLat;
    private double endLat;
    private double midLon;
    private double midLat;
    private String location;
}

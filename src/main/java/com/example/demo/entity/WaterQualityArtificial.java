package com.example.demo.entity;


import lombok.Data;

//水质--人工
@Data
public class WaterQualityArtificial {
    private int id;
    private String name;
    private String time;
    private double codcr;
    private double ammonia;
    private double phosphorous;
    private double ph;
    private double lon;
    private double lat;
}

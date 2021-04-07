package com.example.demo.entity;

import lombok.Data;

//水位断面点位
@Data
public class WaterLevel {
    private int id;
    private String name;
    private String time;
    private double waterlevel;
    private double flow;
    private double lon;
    private double lat;
}

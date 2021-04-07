package com.example.demo.entity;

import lombok.Data;

//水位流量曲线
@Data
public class WaterLevelFlow {
    private int id;
    private String name;
    private double flow100;
    private double level100;
    private double flow50;
    private double level50;
    private double flow20;
    private double level20;
}



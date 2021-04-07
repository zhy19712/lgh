package com.example.demo.entity;

import lombok.Data;

@Data
public class WaterClearRt {
    private int id;
    private String name;
    private double waterIn;
    private double waterOut;
    private double an;
    private double pho;
    private double cod;
    private double level;
    private String time;
}

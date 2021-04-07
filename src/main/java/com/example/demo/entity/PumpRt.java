package com.example.demo.entity;

import lombok.Data;

@Data
public class PumpRt {
    private int id;
    private String time;
    private double cod;
    private double flowOut;
    private double flow;
    private double an;
    private double pho;
    private double level;
}

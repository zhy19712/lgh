package com.example.demo.entity;

import lombok.Data;

//水位开度曲线
@Data
public class Openning {
    private int id;
    private String name;
    private double level;
    private double flow;
    private double openning;
}

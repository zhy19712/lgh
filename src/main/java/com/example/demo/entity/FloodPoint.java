package com.example.demo.entity;

import lombok.Data;

@Data
public class FloodPoint {
    private int id;
    private String name;
    private String location;
    private String district;
    private String street;
    private String type;
    private String reason;
    private String process;
    private double area;
    private double depth;
    private double duration;
    private String floodType;
}

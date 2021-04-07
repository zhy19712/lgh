package com.example.demo.entity;

import lombok.Data;

@Data
public class Trail {
    private int id;
    private double lon;
    private double lat;
    private String name;
    private String time;
    private String type;
}

package com.example.demo.entity;

import lombok.Data;

@Data
public class Flight {
    private int id;
    private double lon;
    private double lat;
    private String path;
    private String point;
    private String time;
}

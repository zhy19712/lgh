package com.example.demo.entity;

import lombok.Data;

@Data
public class Task {
    private int id;
    private String time;
    private String name;
    private String task;
    private String type;
    private String detail;
    private double lon;
    private double lat;
    private String person;
    private String picBefore;
    private String picAfter;
    private int status;
    private String executor;
    private int fixed;
    private String demand;
}

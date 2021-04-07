package com.example.demo.entity;

import lombok.Data;

@Data
public class Event {
    private int id;
    private String time;
    private String event;
    private String name;
    private String type;
    private String detail;
    private String demand;
    private String result;
    private double lon;
    private double lat;
    private String person;
    private String picBefore;
    private String picAfter;
    private int bigEvent;
    private int status;
    private String reporter;
    private String files;
}

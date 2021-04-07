package com.example.demo.entity;

import lombok.Data;

@Data
public class TaskFixed {
    private int id;
    private String task;
    private String type;
    private String duration;
    private String time;
    private String person;
    private int status;
    private int fixed;
    private String demand;
}

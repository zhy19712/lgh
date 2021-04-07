package com.example.demo.entity;

import lombok.Data;

@Data
public class Clock {
    private int id;
    private String time;
    private String person;
    private String location;
    private String type;
}

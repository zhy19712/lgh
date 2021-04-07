package com.example.demo.entity;

import lombok.Data;

//涉河施工
@Data
public class Construction {
    private int id;
    private String name;
    private String time;
    private int duration;
    private String constructor;
    private String location;
    private String builder;
    private String detail;
    private String picBefore;
    private String picAfter;
}

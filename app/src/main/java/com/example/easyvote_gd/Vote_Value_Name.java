package com.example.easyvote_gd;

public class Vote_Value_Name {

    int count;
    public String name;

    public Vote_Value_Name() {
    }

    public Vote_Value_Name(int count, String name) {
        this.count = count;
        this.name = name;
    }

    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}

package com.example.navigation;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;
import java.util.Set;

public class ExampleOfOneFilter {

    private String name;
    private int valueOfFilterInt;
    private float valueOfFilterFloat;

    public ExampleOfOneFilter(String name, int valueOfFilter) {
        this.name = name;
        this.valueOfFilterInt = valueOfFilter;
    }
    public ExampleOfOneFilter(String name, float valueOfFilter) {
        this.name = name;
        this.valueOfFilterFloat = valueOfFilter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValueOfFilterInt() {
        return valueOfFilterInt;
    }
    public float getValueOfFilterFloat() {
        return valueOfFilterFloat;
    }

    public void setValueOfFilter(int valueOfFilter) {
        this.valueOfFilterInt = valueOfFilter;
    }
    public void setValueOfFilter(float valueOfFilter) {
        this.valueOfFilterFloat = valueOfFilter;
    }


}

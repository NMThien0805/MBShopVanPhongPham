package com.example.doancuoiki;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ThangThongKe implements Serializable {
    @SerializedName("month")
    private int month;

    public ThangThongKe(int month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "ThangThongKe{" +
                "month=" + month +
                '}';
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}

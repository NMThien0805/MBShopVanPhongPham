package com.example.doancuoiki;

import com.google.gson.annotations.SerializedName;

public class objectOTP {
    @SerializedName("otp")
    String otp;

    @Override
    public String toString() {
        return "objectOTP{" +
                "otp='" + otp + '\'' +
                '}';
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public objectOTP(String otp) {
        this.otp = otp;
    }
}

package com.example.doancuoiki.objectDDH;

import com.google.gson.annotations.SerializedName;

public class truyenDDH {
    @SerializedName("UserId")
    String UserId;
    @SerializedName("TT")
    String TT;

    public truyenDDH(String UserId, String TT) {
        this.UserId = UserId;
        this.TT = TT;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getTT() {
        return TT;
    }

    public void setTT(String TT) {
        this.TT = TT;
    }

    @Override
    public String toString() {
        return "truyenDDH{" +
                "UserId='" + UserId + '\'' +
                ", TT='" + TT + '\'' +
                '}';
    }
}

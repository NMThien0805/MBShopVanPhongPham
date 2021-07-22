package com.example.doancuoiki.objectDDH;

import com.google.gson.annotations.SerializedName;

public class resDDH {
    @SerializedName("_id")
    private String id;
    @SerializedName("UserId")
    private String userid;
    @SerializedName("NgayDat")
    private String ngaydat;
    @SerializedName("TT")
    private String tt;

    public resDDH(String id, String userid, String ngaydat, String tt) {
        this.id = id;
        this.userid = userid;
        this.ngaydat = ngaydat;
        this.tt = tt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getNgaydat() {
        return ngaydat;
    }

    public void setNgaydat(String ngaydat) {
        this.ngaydat = ngaydat;
    }

    public String getTt() {
        return tt;
    }

    public void setTt(String tt) {
        this.tt = tt;
    }

    @Override
    public String toString() {
        return "resDDH{" +
                "id='" + id + '\'' +
                ", userid='" + userid + '\'' +
                ", ngaydat='" + ngaydat + '\'' +
                ", tt='" + tt + '\'' +
                '}';
    }
}

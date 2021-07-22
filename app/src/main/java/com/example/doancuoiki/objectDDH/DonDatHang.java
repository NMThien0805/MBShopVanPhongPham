package com.example.doancuoiki.objectDDH;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DonDatHang {
    @SerializedName("_id")
    private String id;
    @SerializedName("UserId")
    private String userid;
    @SerializedName("NgayDat")
    private String ngaydat;
    @SerializedName("TT")
    private String tt;

    private List<CTDDH> ctddh;


    @Override
    public String toString() {
        return "DonDatHang{" +
                "id='" + id + '\'' +
                ", userid='" + userid + '\'' +
                ", ngaydat='" + ngaydat + '\'' +
                ", tt='" + tt + '\'' +
                ", ctddh=" + ctddh +
                '}';
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

    public List<CTDDH> getCtddh() {
        return ctddh;
    }

    public void setCtddh(List<CTDDH> ctddh) {
        this.ctddh = ctddh;
    }

    public DonDatHang(String id, String userid, String ngaydat, String tt, List<CTDDH> ctddh) {
        this.id = id;
        this.userid = userid;
        this.ngaydat = ngaydat;
        this.tt = tt;
        this.ctddh = ctddh;
    }
}

package com.example.doancuoiki.objectDDH;

import com.google.gson.annotations.SerializedName;

public class CTDDH {
//    @SerializedName("_id")
//    private String id;
    @SerializedName("IdDDH")
    private String idddh;
    @SerializedName("IdSP")
    private String idsp;
    @SerializedName("Soluong")
    private int soluong;
    @SerializedName("DonGia")
    private int dongia;


    public CTDDH(String idddh, String idsp, int soluong, int dongia) {
//        this.id = id;
        this.idddh = idddh;
        this.idsp = idsp;
        this.soluong = soluong;
        this.dongia = dongia;
    }

    public CTDDH() {

    }

    @Override
    public String toString() {
        return "CTDDH{" +
//                "id='" + id + '\'' +
                "idddh='" + idddh + '\'' +
                ", idsp='" + idsp + '\'' +
                ", soluong=" + soluong +
                ", dongia=" + dongia +
                '}';
    }

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public String getIdddh() {
        return idddh;
    }

    public void setIdddh(String idddh) {
        this.idddh = idddh;
    }

    public String getIdsp() {
        return idsp;
    }

    public void setIdsp(String idsp) {
        this.idsp = idsp;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getDongia() {
        return dongia;
    }

    public void setDongia(int dongia) {
        this.dongia = dongia;
    }
}

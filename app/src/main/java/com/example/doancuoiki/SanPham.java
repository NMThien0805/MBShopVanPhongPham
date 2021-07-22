package com.example.doancuoiki;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;

public class SanPham implements Serializable {

    @SerializedName("TenSP")
    String TenSP;
    @SerializedName("Soluong")
    int SoLuong;
    @SerializedName("IdLoai")
    String Loai;
    @SerializedName("Gia")
    int gia;
    @SerializedName("img")
    String img;

    public SanPham(String tenSP, int soLuong, String loai, int gia, String img) {
        TenSP = tenSP;
        SoLuong = soLuong;
        Loai = loai;
        this.gia = gia;
        this.img = img;
    }

    @Override
    public String toString() {
        return "SanPham{" +
                "TenSP='" + TenSP + '\'' +
                ", SoLuong=" + SoLuong +
                ", Loai='" + Loai + '\'' +
                ", gia=" + gia +
                ", img='" + img + '\'' +
                '}';
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public String getLoai() {
        return Loai;
    }

    public void setLoai(String loai) {
        Loai = loai;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}

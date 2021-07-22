package com.example.doancuoiki.Class;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Product {

    @SerializedName("_id")
    private String id;
    @SerializedName("TenSP")
    private String tensp;
    @SerializedName("Gia")
    private int Gia;
    @SerializedName("Soluong")
    private int soluong;
    @SerializedName("IdLoai")
    private String idloai;
    @SerializedName("date")
    private String date;
    @SerializedName("img")
    private String img;


    private int numProduct;

    public Product() {

    }

    public Product(String id, String tensp, int gia, int soluong, String idloai, String date, String img, int numProduct) {
        this.id = id;
        this.tensp = tensp;
        Gia = gia;
        this.soluong = soluong;
        this.idloai = idloai;
        this.date = date;
        this.img = img;
        this.numProduct = numProduct;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", tensp='" + tensp + '\'' +
                ", Gia=" + Gia +
                ", soluong=" + soluong +
                ", idloai='" + idloai + '\'' +
                ", date='" + date + '\'' +
                ", img='" + img + '\'' +
                ", numProduct=" + numProduct +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public int getGia() {
        return Gia;
    }

    public void setGia(int gia) {
        Gia = gia;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getIdloai() {
        return idloai;
    }

    public void setIdloai(String idloai) {
        this.idloai = idloai;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getNumProduct() {
        return numProduct;
    }

    public void setNumProduct(int numProduct) {
        this.numProduct = numProduct;
    }
}
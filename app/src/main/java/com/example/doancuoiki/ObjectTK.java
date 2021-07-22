package com.example.doancuoiki;

import com.example.doancuoiki.Class.Product;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ObjectTK implements Serializable {
    @SerializedName("product")
    private Product product;
    @SerializedName("sum")
    private int tong;

    public ObjectTK() {
    }

    public ObjectTK(Product product, int tong) {
        this.product = product;
        this.tong = tong;
    }

    @Override
    public String toString() {
        return "ObjectTK{" +
                "product=" + product +
                ", tong=" + tong +
                '}';
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getTong() {
        return tong;
    }

    public void setTong(int tong) {
        this.tong = tong;
    }
}

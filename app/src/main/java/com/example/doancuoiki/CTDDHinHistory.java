package com.example.doancuoiki;

import com.example.doancuoiki.Class.Product;
import com.example.doancuoiki.objectDDH.CTDDH;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

public class CTDDHinHistory {
    @SerializedName("listCTDDH")
    private List<CTDDH> ctddh;
    @SerializedName("listProduct")
    private List<Product>  product;

    public CTDDHinHistory(List<CTDDH> ctddh, List<Product> product) {
        this.ctddh = ctddh;
        this.product = product;
    }


    public CTDDHinHistory() {

    }

    @Override
    public String toString() {
        return "CTDDHinHistory{" +
                "ctddh=" + ctddh +
                ", product=" + product +
                '}';
    }

    public List<CTDDH> getCtddh() {
        return ctddh;
    }

    public void setCtddh(List<CTDDH> ctddh) {
        this.ctddh = ctddh;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }
}

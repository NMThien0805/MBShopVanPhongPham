package com.example.doancuoiki;

import com.example.doancuoiki.objectDDH.CTDDH;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TT_CTDDH_SP  implements Serializable {
    @SerializedName("listProduct")
    List<SanPham> listsp;
    @SerializedName("listCTDDH")
    List<CTDDH> ctddhList;


    @Override
    public String toString() {
        return "TT_CTDDH_SP{" +
                "listsp=" + listsp +
                ", ctddhList=" + ctddhList +
                '}';
    }

    public TT_CTDDH_SP() {
    }

    public TT_CTDDH_SP(List<SanPham> listsp, List<CTDDH> ctddhList) {
        this.listsp = listsp;
        this.ctddhList = ctddhList;
    }

    public List<SanPham> getListsp() {
        return listsp;
    }

    public void setListsp(List<SanPham> listsp) {
        this.listsp = listsp;
    }

    public List<CTDDH> getCtddhList() {
        return ctddhList;
    }

    public void setCtddhList(List<CTDDH> ctddhList) {
        this.ctddhList = ctddhList;
    }
}

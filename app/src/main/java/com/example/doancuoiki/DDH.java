package com.example.doancuoiki;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DDH implements Serializable {

    @SerializedName("_id")
    private String _id;
    @SerializedName("UserId")
    private String UserID;
    @SerializedName("TT")
    private String TrangThai;
    @SerializedName("NgayDat")
    private String ngaydat;


    public DDH(String userID, String trangThai) {
        UserID = userID;
        TrangThai = trangThai;
    }

//    public DDH(String id, String userID, String trangThai, String ngaydat) {
//        this.id = id;
//        UserID = userID;
//        TrangThai = trangThai;
//        this.ngaydat = ngaydat;
//    }

    public DDH() {
    }

    @Override
    public String toString() {
        return "DDH{" +
                "_id='" + _id + '\'' +
                ", UserID='" + UserID + '\'' +
                ", TrangThai='" + TrangThai + '\'' +
                ", ngaydat='" + ngaydat + '\'' +
                '}';
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }

    public String getNgaydat() {
        return ngaydat;
    }

    public void setNgaydat(String ngaydat) {
        this.ngaydat = ngaydat;
    }
}

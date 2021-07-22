package com.example.doancuoiki;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserLogged implements Serializable {

    @SerializedName("_id")
    private  String id;
    @SerializedName("name")
    private String name;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("GioiTinh")
    private boolean gioitinh;
    @SerializedName("email")
    private String mail;
    @SerializedName("DiaChi")
    private String diachi;
    @SerializedName("SDT")
    private String sdt;
    @SerializedName("Role")
    private String role;
    @SerializedName("img")
    private String img;
    @SerializedName("date")
    private String date;

    public UserLogged() {
    }

    public UserLogged(String id, String name, String username, String password, boolean gioitinh, String mail, String diachi, String sdt, String role, String img, String date) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.gioitinh = gioitinh;
        this.mail = mail;
        this.diachi = diachi;
        this.sdt = sdt;
        this.role = role;
        this.img = img;
        this.date = date;
    }

    @Override
    public String toString() {
        return "UserLogged{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", gioitinh=" + gioitinh +
                ", mail='" + mail + '\'' +
                ", diachi='" + diachi + '\'' +
                ", sdt='" + sdt + '\'' +
                ", role='" + role + '\'' +
                ", img='" + img + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(boolean gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

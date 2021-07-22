package com.example.doancuoiki.objectQuenMK;

import com.google.gson.annotations.SerializedName;

public class object_confirm_pass {
    @SerializedName("email")
    String email;
    @SerializedName("confirm_token")
    String confirm_token;
    @SerializedName("newpass")
    String newpass;

    public object_confirm_pass(String email, String confirm_token, String newpass) {
        this.email = email;
        this.confirm_token = confirm_token;
        this.newpass = newpass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirm_token() {
        return confirm_token;
    }

    public void setConfirm_token(String confirm_token) {
        this.confirm_token = confirm_token;
    }

    public String getNewpass() {
        return newpass;
    }

    public void setNewpass(String newpass) {
        this.newpass = newpass;
    }

    @Override
    public String toString() {
        return "object_confirm_pass{" +
                "email='" + email + '\'' +
                ", confirm_token='" + confirm_token + '\'' +
                ", newpass='" + newpass + '\'' +
                '}';
    }
}

package com.example.doancuoiki;

import android.content.Context;

import com.google.gson.Gson;

public class DataLocalManage {
    private static final String FREF_OBJECT_USER ="FREF_OBJECT_USER";
    private static DataLocalManage instance;
    private SharePreferences sharePreferences;

    public static void init (Context context) {
        instance = new DataLocalManage();
        instance.sharePreferences= new SharePreferences(context);
    }

    public static DataLocalManage getInstance(){
        if(instance==null){
            instance=new DataLocalManage();
        }
        return instance;
    }
    public static void setUser(UserLogged user){
        Gson gson = new Gson();
        String strJSON= gson.toJson(user);
        DataLocalManage.getInstance().sharePreferences.putStringValue(FREF_OBJECT_USER,strJSON);
    }
    public static Users getUser(){
        String strJSON= DataLocalManage.getInstance().sharePreferences.getStringValue(FREF_OBJECT_USER);
        Gson gson = new Gson();
        Users user = gson.fromJson(strJSON, Users.class);
        return user;

    }
}

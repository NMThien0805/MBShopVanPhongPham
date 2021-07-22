package com.example.doancuoiki;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferences {

    private static final String SHARE_FREFENCENCES = "SHARE_FREFENCENCES";
    private Context mContext;

    public SharePreferences(Context mContext) {
        this.mContext = mContext;
    }
    public void putStringValue(String key,String value){
        SharedPreferences sharedPreferences =mContext.getSharedPreferences(SHARE_FREFENCENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }
    public String getStringValue(String key){
        SharedPreferences sharedPreferences =mContext.getSharedPreferences(SHARE_FREFENCENCES,Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"");

    }
















}

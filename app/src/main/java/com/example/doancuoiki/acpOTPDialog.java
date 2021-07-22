package com.example.doancuoiki;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.doancuoiki.api.ApiService;
import com.example.doancuoiki.objectDDH.DonDatHang;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class acpOTPDialog {

    Context context;
    UserLogged userlogged;
    DonDatHang ddhIn;

    public acpOTPDialog(Context context, UserLogged userlogged, DonDatHang ddhIn) {
        this.context = context;
        this.userlogged = userlogged;
        this.ddhIn = ddhIn;
    }

    public DonDatHang getDdhIn() {
        return ddhIn;
    }

    public void setDdhIn(DonDatHang ddhIn) {
        this.ddhIn = ddhIn;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public UserLogged getUserlogged() {
        return userlogged;
    }

    public void setUserlogged(UserLogged userlogged) {
        this.userlogged = userlogged;
    }

    public void action(){
        Call<objectOTP> res = ApiService.apiService.otpDDH(getUserlogged());
        res.enqueue(new Callback<objectOTP>() {
            @Override
            public void onResponse(Call<objectOTP> call, Response<objectOTP> response) {
                System.out.println(response.body().otp);
                String otp = response.body().getOtp();
                openDialog(Gravity.CENTER, otp);
            }

            @Override
            public void onFailure(Call<objectOTP> call, Throwable t) {
                System.out.println("Fail OTP DDH");
            }
        });
    }

    private void openDialog(int gratity, String otp) {
        final Dialog dialog =  new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_acpotp);

        Window window = dialog.getWindow();
        if(window==null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAtt = window.getAttributes();
        windowAtt.gravity= gratity;
        window.setAttributes(windowAtt);

        if(Gravity.BOTTOM==gratity){
            dialog.setCancelable(true);

        }else{
            dialog.setCancelable(false);
        }

        //anh xa
        EditText edtOTP = (EditText) dialog.findViewById(R.id.edtOTP);
        Button btnAcpOTP = (Button) dialog.findViewById(R.id.btnAcpOTP);
        Button btnBackOTP = (Button) dialog.findViewById(R.id.btnBackOTP);

        btnBackOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });


        btnAcpOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otpIn = edtOTP.getText().toString().trim();
                if(otpIn.equals(otp)){

                    ApiService.apiService.acpOTP(ddhIn).enqueue(new Callback<DonDatHang>() {
                        @Override
                        public void onResponse(Call<DonDatHang> call, Response<DonDatHang> response) {
                            System.out.println(response.toString());
                            Toast.makeText(getContext(), "Thanh Cong", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<DonDatHang> call, Throwable t) {
                            System.out.println("Fail acp OTP");
                        }
                    });
//                    Call<DonDatHang> res1 = ApiService.apiService.acpOTP(ddhIn);
//                    res1.enqueue(new Callback<DonDatHang>() {
//                        @Override
//                        public void onResponse(Call<DDH> call, Response<DDH> response) {
//                            System.out.println(response.toString());
//                            Toast.makeText(getContext(), "Thanh Cong", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onFailure(Call<DDH> call, Throwable t) {
//                            System.out.println("Fail acp OTP");
//                        }
//                    });
                }
                else Toast.makeText(context, "Key OTP wrong!!!", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }
}

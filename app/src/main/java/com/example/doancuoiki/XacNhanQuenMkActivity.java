package com.example.doancuoiki;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doancuoiki.api.ApiService;
import com.example.doancuoiki.objectQuenMK.object_confirm_pass;
import com.example.doancuoiki.objectQuenMK.resXacNhanQuenMK;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class XacNhanQuenMkActivity extends AppCompatActivity {

    EditText edtMaXacNhan, edtPassXacNhanMK, edtPassXacNhanMKLan2, edtemailxacnhan;
    Button btnXacNhanQuenMK, btnThoatXacNhanQUenMK;
    TextView tViewSendAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_nhan_quen_mk);

        setControl();
        setEvent();
    }

    void setControl(){
        edtMaXacNhan = findViewById(R.id.edtMaXacNhan);
        edtPassXacNhanMK = findViewById(R.id.edtPassXacNhanMK);
        edtPassXacNhanMKLan2 = findViewById(R.id.edtPassXacNhanMKLan2);
        btnXacNhanQuenMK = findViewById(R.id.btnXacNhanQuenMK);
        btnThoatXacNhanQUenMK = findViewById(R.id.btnThoatXacNhanQUenMK);
        tViewSendAgain = findViewById(R.id.tViewSendAgain);
        edtemailxacnhan = findViewById(R.id.edtemailxacnhan);
    }

    void setEvent(){
        tViewSendAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(XacNhanQuenMkActivity.this, QuenMKActivity.class);
                startActivity(intent);
            }
        });
        btnThoatXacNhanQUenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(XacNhanQuenMkActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnXacNhanQuenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtemailxacnhan.getText().length() > 0){
                    if(edtMaXacNhan.getText().length() > 0){
                        if(edtPassXacNhanMK.getText().toString().trim().equals(edtPassXacNhanMKLan2.getText().toString().trim())){
                            Call<resXacNhanQuenMK> res = ApiService.apiService
                                    .confirmForgotPass(new object_confirm_pass(edtemailxacnhan.getText().toString().trim(),
                                            "apitoken " + edtMaXacNhan.getText().toString().trim(),
                                            edtPassXacNhanMKLan2.getText().toString().trim()));
                            res.enqueue(new Callback<resXacNhanQuenMK>() {
                                @Override
                                public void onResponse(Call<resXacNhanQuenMK> call, Response<resXacNhanQuenMK> response) {
                                    System.out.println("okokok");
                                    Toast.makeText(XacNhanQuenMkActivity.this, "Changed!!!", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<resXacNhanQuenMK> call, Throwable t) {
                                    System.out.println("Fail change" + t);
                                    Toast.makeText(XacNhanQuenMkActivity.this, "Call api change pass fail", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                        else{
                            Toast.makeText(XacNhanQuenMkActivity.this, "Pass >< pass confirm", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(XacNhanQuenMkActivity.this, "Nhap key!!!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(XacNhanQuenMkActivity.this, "Nhap email!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
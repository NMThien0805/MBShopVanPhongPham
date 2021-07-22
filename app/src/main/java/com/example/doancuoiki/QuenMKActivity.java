package com.example.doancuoiki;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.doancuoiki.api.ApiService;
import com.example.doancuoiki.objectQuenMK.resXacNhanQuenMK;
import com.example.doancuoiki.objectQuenMK.token_forgotpass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuenMKActivity extends AppCompatActivity {

    EditText email;
    Button btnSend, btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_m_k);

        setControl();
        setEvent();
    }

    void setControl(){
        email = (EditText)findViewById(R.id.edtUserQuenMK);
        btnSend = findViewById(R.id.btnGuiMaXacNhanQuenMK);
        btnExit = findViewById(R.id.btnThoatQuenMK);
    }

    void setEvent(){
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_to_email(email.getText().toString().trim());
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuenMKActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void send_to_email(String email) {
        String s = email.trim();
        System.out.println("resuslt: " + s);
        Call<token_forgotpass> res = ApiService.apiService.forgotPass(new resXacNhanQuenMK(s));
        res.enqueue(new Callback<token_forgotpass>() {
            @Override
            public void onResponse(Call<token_forgotpass> call, Response<token_forgotpass> response) {
                System.out.println(response.toString());
                if(response.code() == 200){
                    AlertDialog.Builder b = new AlertDialog.Builder(QuenMKActivity.this);
                    //Thiết lập tiêu đề
                    b.setTitle("Message");
                    b.setMessage("Exit -> Check your mail -> get key -> confirm forgot password \n(<!> The key will invalid after 5min)");
                    // Nút Ok
                    b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });
                    //Nút Cancel
//                    b.setNegativeButton("Không đồng ý", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            dialog.cancel();
//                        }
//                    });
                    //Tạo dialog
                    AlertDialog al = b.create();
                    //Hiển thị
                    al.show();
                }
            }

            @Override
            public void onFailure(Call<token_forgotpass> call, Throwable t) {
                System.out.println("Call api send email fail");
            }
        });
    }
}
package com.example.doancuoiki;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.doancuoiki.Class.Product;
import com.example.doancuoiki.admin.adapter.CTinDHAdapter;
import com.example.doancuoiki.api.ApiService;
import com.example.doancuoiki.objectDDH.CTDDH;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CTinDHActivity extends AppCompatActivity {
    Context context;
    RecyclerView recyclerView;
    CTinDHAdapter adapter;
    List<CTDDH> ctddhList = new ArrayList<>();
    List<SanPham> products = new ArrayList<>();
    TT_CTDDH_SP tam =new TT_CTDDH_SP();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_tin_d_h);
        recyclerView = (RecyclerView) findViewById(R.id.rv_listSPinDDH);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        DDH ddh = (DDH) bundle.getSerializable("dataDDH");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);

        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new CTinDHAdapter(this, R.layout.item_sp_in_ddh, products, ctddhList);

        CallAPI(ddh);
        System.out.println(tam);
        recyclerView.setAdapter(adapter);

    }

    private void CallAPI(DDH ddh) {
//        System.out.println(ddh.toString());
        Call<TT_CTDDH_SP> listCall = ApiService.apiService.getCTDDH(ddh);
        listCall.enqueue(new Callback<TT_CTDDH_SP>() {
            @Override
            public void onResponse(Call<TT_CTDDH_SP> call, Response<TT_CTDDH_SP> response) {
                tam = response.body();
                products = tam.getListsp();
                ctddhList=tam.getCtddhList();
                System.out.println("trong hàm"+products);
                adapter.setData(ctddhList,products);

            }

            @Override
            public void onFailure(Call<TT_CTDDH_SP> call, Throwable t) {
                System.out.println("false" + t.toString());

            }
        });
    }
}
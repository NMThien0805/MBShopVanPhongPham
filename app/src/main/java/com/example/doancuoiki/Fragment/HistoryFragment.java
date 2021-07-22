package com.example.doancuoiki.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiki.Adapter.HistoryProductAdapter;
import com.example.doancuoiki.CTDDHinHistory;
import com.example.doancuoiki.Class.Product;
import com.example.doancuoiki.DataLocalManage;
import com.example.doancuoiki.Home;
import com.example.doancuoiki.R;
import com.example.doancuoiki.UserLogged;
import com.example.doancuoiki.api.ApiService;
import com.example.doancuoiki.objectDDH.CTDDH;
import com.example.doancuoiki.objectDDH.DonDatHang;
import com.google.android.gms.common.api.Api;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFragment extends Fragment {

    // region Variable

    private Home home;
    private List<DonDatHang> listOrder;
    private List<CTDDH> listDetailOrder;

    private View mView;
    private EditText edtHistoryPhone;
    private Button btnHistorySearch;
    private RecyclerView rcvHitorySearch;

    private HistoryProductAdapter historyProductAdapter;

    UserLogged users;

    public UserLogged getUsers() {
        return users;
    }

    public void setUsers(UserLogged users) {
        this.users = users;
    }
    // endregion Variable

    public HistoryFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();

        // Khi quay lại từ fragment OrderInfo sẽ thực hiện tìm kiếm lại
//        if (!edtHistoryPhone.getText().toString().trim().isEmpty()){
//            findOrder();
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle=getArguments();
        if(bundle!=null){
            setUsers((UserLogged) bundle.getSerializable("object_user"));
            DataLocalManage.setUser(getUsers());
        }



        mView =  inflater.inflate(R.layout.fragment_history, container, false);

        initItem();

        Toast.makeText(home, getUsers().getId(), Toast.LENGTH_SHORT).show();
        System.out.println();
        return mView;
    }

    // region Private menthod

    // Khởi tạo các item
    private void initItem(){
        listOrder = new ArrayList<>();
        listDetailOrder = new ArrayList<>();

        home = (Home) getActivity();

        historyProductAdapter = new HistoryProductAdapter();

        selectAllCTDDHhistory();
        rcvHitorySearch = mView.findViewById(R.id.rcv_hitory_search);
    }


    private void selectAllCTDDHhistory() {


        ApiService.apiService.selectDDHHistory(getUsers()).enqueue(new Callback<List<DonDatHang>>() {
            @Override
            public void onResponse(Call<List<DonDatHang>> call, Response<List<DonDatHang>> response) {

                List<DonDatHang> listddh = new ArrayList<>();
                listddh = response.body();
                System.out.println(listddh);
                historyProductAdapter.setData(listddh,getUsers(),home);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(home,RecyclerView.VERTICAL,false);
                rcvHitorySearch.setLayoutManager(linearLayoutManager);
                rcvHitorySearch.setAdapter(historyProductAdapter);
            }

            @Override
            public void onFailure(Call<List<DonDatHang>> call, Throwable t) {

            }
        });
//        ApiService.apiService.selectCTDDHHistory(getUsers()).enqueue(new Callback<CTDDHinHistory>() {
//            @Override
//            public void onResponse(Call<CTDDHinHistory> call, Response<CTDDHinHistory> response) {
//                CTDDHinHistory listmain = new CTDDHinHistory();
//                listmain = response.body();
//                List<CTDDH> listctddh = new ArrayList<>();
//                List<Product> listproduct = new ArrayList<>();
//                listctddh = listmain.getCtddh();
//                listproduct = listmain.getProduct();
//
//                historyProductAdapter.setData(listctddh, listproduct, home);
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(home, RecyclerView.VERTICAL, false);
//                rcvHitorySearch.setLayoutManager(linearLayoutManager);
//                rcvHitorySearch.setAdapter(historyProductAdapter);
//            }
//
//            @Override
//            public void onFailure(Call<CTDDHinHistory> call, Throwable t) {
//
//            }
    }

}
package com.example.doancuoiki.admin.thongke;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.doancuoiki.DDH;
import com.example.doancuoiki.R;
import com.example.doancuoiki.SanPham;
import com.example.doancuoiki.admin.adapter.DDHAdapter;
import com.example.doancuoiki.admin.adapter.ProductAdapter;
import com.example.doancuoiki.api.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DDHFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DDHFragment extends Fragment {
    View v;
    DDHAdapter ddhAdapter;
    RecyclerView recyclerView;

    List<DDH> list1 =new ArrayList<>();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DDHFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DDHFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DDHFragment newInstance(String param1, String param2) {
        DDHFragment fragment = new DDHFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //list1 = new ArrayList<>();
        //getListDDH(list1);
    }

    private List<DDH> getListDDH(List<DDH> list1) {
//        list1.add(new DDH(123,"22/12/2020",12));
//        list1.add(new DDH(1234,"22/12/2020",12));
//        list1.add(new DDH(1235,"22/12/2020",12));
//        list1.add(new DDH(1236,"22/12/2020",12));

        return list1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_ddh, container, false);
        recyclerView=(RecyclerView)v.findViewById(R.id.rcv_listDDH);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);



        ddhAdapter = new DDHAdapter(getContext(),list1);
        CallAPI();

        recyclerView.setAdapter(ddhAdapter);


        return v;
    }
    private void CallAPI() {
        Call<List<DDH>> listCall = ApiService.apiService.getAllDDH();
        listCall.enqueue(new Callback<List<DDH>>() {
            @Override
            public void onResponse(Call<List<DDH>> call, Response<List<DDH>> response) {
                List<DDH> list3= new ArrayList<>();
                list3 = response.body();
                for(int i=0;i<list3.size();i++) {
                    list1.add(list3.get(i));

                }
                System.out.println(response.body());
                ddhAdapter.setData(list1);

            }
            @Override
            public void onFailure(Call<List<DDH>> call, Throwable t) {
                Toast.makeText(getContext(), "Call API Error", Toast.LENGTH_SHORT).show();
                Log.d("false", t.toString());
            }
        });

    }
}
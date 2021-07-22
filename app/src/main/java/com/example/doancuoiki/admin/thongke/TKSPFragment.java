package com.example.doancuoiki.admin.thongke;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.doancuoiki.Class.Product;
import com.example.doancuoiki.DDH;
import com.example.doancuoiki.ObjectTK;
import com.example.doancuoiki.R;
import com.example.doancuoiki.ThangThongKe;
import com.example.doancuoiki.api.ApiService;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TKSPFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TKSPFragment extends Fragment {
    View v;
    public PieChart pieChart;
    Button btnChonThang,btnThoat;
    LinearLayout layoutthang;
    TextView thangchon;
    String thang;
    ThangThongKe thangThongKe= new ThangThongKe(1);
    List<ObjectTK> objectTKlist = new ArrayList<>();
//    List<Product> productList= new ArrayList<>();
//    List<Integer> listsum = new ArrayList<Integer>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TKSPFragment() {
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
    public static TKSPFragment newInstance(String param1, String param2) {
        TKSPFragment fragment = new TKSPFragment();
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

    }

    private void LoadPieChartData() {
        /*ArrayList arr1 = ketnoi.Thongke1(thang);
        ArrayList arr2 = ketnoi.Thongke2(thang);
        ArrayList<PieEntry> entries = new ArrayList<>();
        for(int i =0;i<arr1.size();i++){
            entries.add(new PieEntry(Integer.valueOf(arr2.get(i).toString()),arr1.get(i).toString()));
        }*/

        //objectTKlist
        System.out.println("load dữ liệu"+objectTKlist.toString());



        ArrayList<PieEntry> entries = new ArrayList<>();
        for(ObjectTK objectTK:objectTKlist){
            entries.add(new PieEntry(objectTK.getTong(),objectTK.getProduct().getTensp()));
        }
//        entries.add(new PieEntry(30,"sach 1"));
//        entries.add(new PieEntry(20,"but bi"));
//        entries.add(new PieEntry(50,"but chi"));
//        entries.add(new PieEntry(50,"sach 2"));
//        entries.add(new PieEntry(20,"sach 3"));

        ArrayList<Integer> colors = new ArrayList<>();
        for(int color: ColorTemplate.MATERIAL_COLORS){
            colors.add(color);
        }
        for(int color: ColorTemplate.VORDIPLOM_COLORS){
            colors.add(color);
        }
        PieDataSet dataSet = new PieDataSet(entries,"category");
        dataSet.setColors(colors);

        PieData data =  new PieData(dataSet);
        data.setDrawValues(true);
        //data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EasingOption.EaseInBounce);
    }

    private void SetupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("CATEGORY");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_t_k_sp, container, false);
        pieChart = (PieChart) v.findViewById(R.id.piechartxx);
        layoutthang=(LinearLayout)v.findViewById(R.id.layoutThang);
        layoutthang.setVisibility(View.INVISIBLE);
        thangchon=(TextView)v.findViewById(R.id.thang);
        pieChart.setVisibility(View.INVISIBLE);
        btnChonThang=(Button)v.findViewById(R.id.btnChonThang);
        //btnThoat=(Button)v.findViewById(R.id.btnThoat);

       btnChonThang.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String[] datas = {"1", "2", "3","4", "5", "6","7", "8", "9","10","11","12"};

               ArrayList al = new ArrayList();
               AlertDialog.Builder b = new AlertDialog.Builder(getContext());
               b.setTitle("Chọn tháng sẽ thống kê : ");

               b.setSingleChoiceItems(datas, 0, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       thangThongKe.setMonth(Integer.parseInt(datas[which]));
                       thangchon.setText(Integer.parseInt(datas[which])+"");
                      // al.add(datas[which]);
                   }
               });
               // Nút Ok
               b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       //thang= al.get(0).toString();
                       //Toast.makeText(getContext(), thang, Toast.LENGTH_SHORT).show();
                       SetupPieChart();
                       System.out.println(thangThongKe);
                       CallAPI(thangThongKe);
                       //al.clear();
                       dialog.cancel();
                   }
               });
               //Nút Cancel
               b.setNegativeButton("Không đồng ý", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       dialog.cancel();
                   }
               });
               //Tạo dialog
               //AlertDialog al = b.create();
               //Hiển thị
               //al.show();
               b.create();
               b.show();
           }
       });

        return v;
    }
    private void CallAPI(ThangThongKe thang) {
        Call<List<ObjectTK>> listCall = ApiService.apiService.top5(thang);
        listCall.enqueue(new Callback<List<ObjectTK>>() {
            @Override
            public void onResponse(Call<List<ObjectTK>> call, Response<List<ObjectTK>> response) {
                objectTKlist = response.body();
                System.out.println("thành công" + response.code());
                LoadPieChartData();
                pieChart.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<List<ObjectTK>> call, Throwable t) {
                System.out.println("thất bại");
            }
        });


    }
}
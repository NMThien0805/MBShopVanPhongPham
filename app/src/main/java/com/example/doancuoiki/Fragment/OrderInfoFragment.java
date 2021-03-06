package com.example.doancuoiki.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiki.Adapter.OrderInfoAdapter;
import com.example.doancuoiki.Home;
import com.example.doancuoiki.R;
import com.example.doancuoiki.objectDDH.DonDatHang;

import java.text.DecimalFormat;

public class OrderInfoFragment extends Fragment {

    // region Variable

    private DecimalFormat formatPrice = new DecimalFormat("###,###,###");
    public static final String TAG = OrderInfoFragment.class.getName();
    private DonDatHang order;
    private Home home;

    private View mView;
    private Button btnOrderInfoBack;
    private TextView tvOrderInfoNo,tvOrderInfoDate,tvOrderInfoCustName,tvOrderInfoCustAddress
            ,tvOrderInfoCustPhone,tvOrderInfoNum,tvOrderInfoTotal,tvOrderInfoStatus;
    private RecyclerView rcvOrderInfo;

    private OrderInfoAdapter orderInfoAdapter;

    // endregion Variable

    public OrderInfoFragment(DonDatHang orderInfo) {
        order = orderInfo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView =  inflater.inflate(R.layout.fragment_order_info, container, false);

        // Khởi tạo các item
        initItem();

        // Set nội dung cho các item
        setContentOrder();

        // Set data cho OrderInfoAdapter
        setDataOrderInfoAdapter();

        return mView;
    }

    // region Private menthod

    // Khởi tạo các item
    private void initItem(){
        orderInfoAdapter = new OrderInfoAdapter();
        home = (Home) getActivity();
        tvOrderInfoNo = mView.findViewById(R.id.tv_order_info_no);
        tvOrderInfoDate = mView.findViewById(R.id.tv_order_info_date);
        tvOrderInfoCustName = mView.findViewById(R.id.tv_order_info_cust_name);
        tvOrderInfoCustAddress = mView.findViewById(R.id.tv_order_info_cust_address);
        tvOrderInfoCustPhone = mView.findViewById(R.id.tv_order_info_cust_phone);
        tvOrderInfoNum = mView.findViewById(R.id.tv_order_info_num);
        tvOrderInfoTotal = mView.findViewById(R.id.tv_order_info_total);
        tvOrderInfoStatus = mView.findViewById(R.id.tv_order_info_status);
        rcvOrderInfo = mView.findViewById(R.id.rcv_order_info);
        btnOrderInfoBack = mView.findViewById(R.id.btn_order_info_back);
        btnOrderInfoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager() != null){
                    getFragmentManager().popBackStack();
                }
            }
        });
    }

    // Set nội dung cho các item
    private void setContentOrder(){
//        tvOrderInfoNo.setText(order.getOrderNo().toUpperCase());
//        tvOrderInfoDate.setText(order.getDateOrder());
//        tvOrderInfoCustName.setText(order.getCustName());
//        tvOrderInfoCustAddress.setText(order.getCustAddress());
//        tvOrderInfoCustPhone.setText(order.getCustPhone());
//        tvOrderInfoNum.setText(String.valueOf(order.getNumProduct()));
//        tvOrderInfoTotal.setText(formatPrice.format(order.getTotalPrice()) + "VNĐ");
//        tvOrderInfoStatus.setText(order.getStatus());
    }

    // Set data cho OrderInfoAdapter
    private void setDataOrderInfoAdapter(){
        orderInfoAdapter.setData(order.getCtddh());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(home,RecyclerView.VERTICAL,false);
        rcvOrderInfo.setLayoutManager(linearLayoutManager);
        rcvOrderInfo.setAdapter(orderInfoAdapter);
    }

    // endregion Private menthod

}
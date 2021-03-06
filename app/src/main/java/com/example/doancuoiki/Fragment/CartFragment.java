package com.example.doancuoiki.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiki.Adapter.ProductCartAdapter;
import com.example.doancuoiki.Class.Product;
import com.example.doancuoiki.DataLocalManage;
import com.example.doancuoiki.Home;
import com.example.doancuoiki.R;
import com.example.doancuoiki.UserLogged;
import com.example.doancuoiki.api.ApiService;
import com.example.doancuoiki.objectDDH.CTDDH;
import com.example.doancuoiki.objectDDH.resDDH;
import com.example.doancuoiki.objectDDH.truyenDDH;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment {

    // region Variable

    private int totalPrice;
    private View mView;
    private Home home;
    private DecimalFormat format;

    // Item empty cart
    private RelativeLayout rlCartEmpty,rlCart;

    // Item product cart
    private List<Product> listCartProduct;
    private RecyclerView rcvCartProduct;
    private TextView tvCartTotalPrice;
    private EditText edtCartCustName,edtCartCustAddress,edtCartCustPhone;
    private Button btnCartOrder;

    private ProductCartAdapter productCartAdapter;

    UserLogged users;

    public UserLogged getUsers() {
        return users;
    }

    public void setUsers(UserLogged users) {
        this.users = users;
    }
    // endregion Variable

    public CartFragment(List<Product> listCartProduct) {
        this.listCartProduct = listCartProduct;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle=getArguments();
        if(bundle!=null){
            setUsers((UserLogged) bundle.getSerializable("object_user"));
            DataLocalManage.setUser(getUsers());
        }

        mView = inflater.inflate(R.layout.fragment_cart, container, false);

        // Kh???i t???o c??c item
        initItem();

        // Set hi???n th??? c??c view
        setVisibilityView();

        return mView;
    }

    // region Private menthod

    // Kh???i t???o c??c item
    private void initItem(){
        productCartAdapter = new ProductCartAdapter();
        rlCartEmpty = mView.findViewById(R.id.rl_cart_empty);
        rlCart = mView.findViewById(R.id.rl_cart);
        rcvCartProduct = mView.findViewById(R.id.rcv_cart_product);
        tvCartTotalPrice = mView.findViewById(R.id.tv_cart_total_price);
        btnCartOrder = mView.findViewById(R.id.btn_cart_order);
        btnCartOrder.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                // Th??m d??? li???u c??c th??ng tin ???? order
                addDataOrder();
            }
        });

        home = (Home) getActivity();
        format = new DecimalFormat("###,###,###");
    }

    // Set tr???ng th??i hi???n th??? c??c view
    private void setVisibilityView(){
        if (listCartProduct.size() == 0){

            // Hi???n th??? gi??? h??ng r???ng
            setVisibilityEmptyCart();
        }else {

            // Hi???n th??? gi??? h??ng
            setVisibilityCart();
            setDataProductCartAdapter();
        }
    }

    // Hi???n th??? gi??? h??ng
    private void setVisibilityCart(){
        rlCartEmpty.setVisibility(View.GONE);
        rlCart.setVisibility(View.VISIBLE);
        String total = format.format(getTotalPrice());
        tvCartTotalPrice.setText( total +" VN??" );
    }

    // set data ProductCartAdapter
    private void setDataProductCartAdapter(){
        productCartAdapter.setData(listCartProduct,home, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(home,RecyclerView.VERTICAL,false);
        rcvCartProduct.setLayoutManager(linearLayoutManager);
        rcvCartProduct.setAdapter(productCartAdapter);
    }

    // l???y gi?? tr??? t???ng ti???n t???t c??? s???n ph???m trong gi??? h??ng
    private int getTotalPrice(){
        for (Product product : listCartProduct){
            int priceProduct = product.getGia() ;
            totalPrice = totalPrice +  priceProduct * product.getNumProduct();
        }
        return totalPrice;
    }

    // Th??m d??? li???u c??c th??ng tin ???? order
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addDataOrder(){

        truyenDDH input = new truyenDDH(getUsers().getId(), "CD");

        Call<resDDH> res = ApiService.apiService.insertDDH(input);
        System.out.println(input.toString());
        res.enqueue(new Callback<resDDH>() {
            @Override
            public void onResponse(Call<resDDH> call, Response<resDDH> response) {
                System.out.println("RESAPI: " + response.body());
//                IdDDH = response.body().getId();
                Call_APIInsertCTDDH(response.body().getId());
            }

            @Override
            public void onFailure(Call<resDDH> call, Throwable t) {
                System.out.println("Fail");
            }
        });
    }

    void Call_APIInsertCTDDH(String IdDDH){
        List<CTDDH> listDetailOrder = makeDetailOrder(IdDDH);

        // Add th??ng tin detail order
        for (CTDDH detailOrder : listDetailOrder){
            System.out.println(detailOrder.toString());
            Call<CTDDH> result = ApiService.apiService.insertCTDDH(detailOrder);
            result.enqueue(new Callback<CTDDH>() {
                @Override
                public void onResponse(Call<CTDDH> call, Response<CTDDH> response) {
//                    System.out.println(response.body().getIdsp());
                    System.out.println(response.toString());
                }

                @Override
                public void onFailure(Call<CTDDH> call, Throwable t) {
                    System.out.println("Fail insert ctddh");
                }
            });
        }
        Toast.makeText(getContext(),"???? ????ng k?? ????n h??ng",Toast.LENGTH_SHORT).show();
        listCartProduct.clear();
        setVisibilityEmptyCart();
        home.setCountProductInCart(0);
    }

    private List<CTDDH> makeDetailOrder(String IdDDH){
        List<CTDDH> listDetailOrder = new ArrayList<>();
        for (Product product : home.getListCartProduct()){
            CTDDH detailOrder = new CTDDH();
            detailOrder.setIdddh(IdDDH);
            detailOrder.setIdsp(product.getId());
            detailOrder.setDongia(product.getGia());
            detailOrder.setSoluong(product.getNumProduct());
            listDetailOrder.add(detailOrder);
        }
        return listDetailOrder;
    }

    // endregion Private menthod

    // region Public menthod

    // Hi???n th??? gi??? h??ng r???ng
    public void setVisibilityEmptyCart(){
        rlCartEmpty.setVisibility(View.VISIBLE);
        rlCart.setVisibility(View.GONE);
    }

    // Set gi?? tr??? hi???n th??? t???ng ti???n khi t??ng gi???m s??? l?????ng
    // mode = 0 : gi???m
    // mode = 1 : t??ng
    public void setTotalPrice(int mode,int count, int priceProduct ){
        if( mode == 0){
            totalPrice = totalPrice - priceProduct * count;
        }else if (mode == 1){
            totalPrice = totalPrice + priceProduct * count;
        }

        tvCartTotalPrice.setText( format.format(totalPrice) + " VN??");
    }

    // Set s?? l?????ng s???n ph???m sau nh???n n??t t??ng gi???m
    public void setCountForProduct(int possion,int countProduct){
        listCartProduct.get(possion).setNumProduct(countProduct);
    }

    // endregion Public menthod

}
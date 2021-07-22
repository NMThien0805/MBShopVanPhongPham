package com.example.doancuoiki.Adapter;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doancuoiki.CTDDHinHistory;
import com.example.doancuoiki.Class.Product;
import com.example.doancuoiki.Home;
import com.example.doancuoiki.R;
import com.example.doancuoiki.UserLogged;
import com.example.doancuoiki.acpOTPDialog;
import com.example.doancuoiki.api.ApiService;
import com.example.doancuoiki.listCTDDHinDDHAdapter;
import com.example.doancuoiki.objectDDH.CTDDH;
import com.example.doancuoiki.objectDDH.DonDatHang;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryProductAdapter extends RecyclerView.Adapter<HistoryProductAdapter.HistoryProductViewHolder> {

    private DecimalFormat formatPrice = new DecimalFormat("###,###,###");
    private List<DonDatHang> listOrder;
    private UserLogged users;

    //    private List<Product> listProduct;
//    private Order orderInfo;
    private Home home;

    public void setData(List<DonDatHang> listOrder, UserLogged users,Home home) {
        this.listOrder = listOrder;
        this.home = home;
        this.users = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history,parent,false);
        return new  HistoryProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryProductViewHolder holder, int position) {
        DonDatHang order = listOrder.get(position);
        if (order == null){
            Toast.makeText(home, "Bạn không có đơn hàng nào", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            holder.tvMaDDH.setText(order.getId());
            holder.tvUserID.setText(order.getUserid());
            holder.tvNgayDat.setText(order.getNgaydat());
            holder.tvTrangthai.setText(order.getTt());

            holder.btnTesthistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    acpOTPDialog dialog = new acpOTPDialog(home, users, order);
                    dialog.action();
                }
            });

            holder.btnXemCTDDHhistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ApiService.apiService.selectCTDDHinDDH(order).enqueue(new Callback<CTDDHinHistory>() {
                        @Override
                        public void onResponse(Call<CTDDHinHistory> call, Response<CTDDHinHistory> response) {
                            CTDDHinHistory listMain = new CTDDHinHistory();
                            listMain = response.body();
                            List<Product> listproduct = new ArrayList<>();
                            List<CTDDH> listctddh = new ArrayList<>();

                            listproduct = listMain.getProduct();
                            listctddh = listMain.getCtddh();

                            if(listMain == null) {
                                Toast.makeText(home, "Sản phẩm trống", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Dialog dialog = new Dialog(home);
                                dialog.setTitle("Product in order");
                                dialog.setCancelable(false);
                                dialog.setContentView(R.layout.dialog_listctddh);
                                TextView tvidddh = (TextView)dialog.findViewById(R.id.tvmaddhinlistctddh);
                                TextView tvngaydatinlistctddh = (TextView)dialog.findViewById(R.id.tvngaydatinlistctddh);
                                Button btnBackCTDDHinDDH = (Button)dialog.findViewById(R.id.btnBackCTDDHinDDH);


                                tvidddh.setText(order.getId());
                                tvngaydatinlistctddh.setText(order.getNgaydat());

                                ListView lv = (ListView)dialog.findViewById(R.id.lvctddhinddh);
                                listCTDDHinDDHAdapter adapter = new listCTDDHinDDHAdapter(listctddh, listproduct,  home);
                                lv.setAdapter(adapter);
                                Toast.makeText(home, "Da vao", Toast.LENGTH_SHORT).show();
                                btnBackCTDDHinDDH.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.cancel();
                                    }
                                });
                                dialog.show();
                            }

                        }

                        @Override
                        public void onFailure(Call<CTDDHinHistory> call, Throwable t) {

                        }
                    });

                }
            });
        }
    }





    @Override
    public int getItemCount() {
        if (listOrder.size() != 0){
            return listOrder.size();
        }else {
            return 0;
        }
    }

    private byte[] chuyen_Bitmap_sang_ArrByte(Bitmap bitmap) {
        ByteArrayOutputStream arr = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, arr);
        byte[] HinhAnh = arr.toByteArray();
//        System.out.println("show" + HinhAnh);
        return HinhAnh;
    }

    public Bitmap chuyen_ArrByte_sang_Bitmap(byte[] img){
//        System.out.println("show" + img);
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        return  bitmap;
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public class HistoryProductViewHolder extends RecyclerView.ViewHolder{

        TextView tvMaDDH,tvUserID, tvNgayDat, tvTrangthai;
        Button btnXemCTDDHhistory,btnTesthistory;

        public HistoryProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaDDH = itemView.findViewById(R.id.tv_idddhhistory);
            tvUserID = itemView.findViewById(R.id.tv_useridhistory);
            tvNgayDat = itemView.findViewById(R.id.tv_ngaydathistory);
            tvTrangthai = itemView.findViewById(R.id.tv_trangthaihistory);
            btnXemCTDDHhistory = itemView.findViewById(R.id.btnXemCTDDHhistory);
            btnTesthistory = itemView.findViewById(R.id.btnTesthistory);
        }
    }
}


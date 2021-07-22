package com.example.doancuoiki.admin.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiki.Class.Product;
import com.example.doancuoiki.R;
import com.example.doancuoiki.SanPham;
import com.example.doancuoiki.objectDDH.CTDDH;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class CTinDHAdapter extends RecyclerView.Adapter {
    Context context;
    private int layout;
    List<SanPham> products;
    List<CTDDH> ctddhList;
    //List<DT_CT> dt_cts;


    public CTinDHAdapter(Context context, int layout, List<SanPham> products, List<CTDDH> ctddhList) {
        this.context = context;
        this.layout = layout;
        this.products = products;
        this.ctddhList = ctddhList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(context);
        View v =layoutInflater.inflate(R.layout.item_sp_in_ddh,parent,false);


        return new ViewHolderClass(v);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass viewHolderClass = (ViewHolderClass)holder;
        CTDDH ctddh = ctddhList.get(position);
        SanPham sp = products.get(position);
        Picasso.get().load(sp.getImg()).into(viewHolderClass.imageView);
        viewHolderClass.tv_tensp.setText(sp.getTenSP());
        viewHolderClass.tv_soluong.setText(ctddh.getSoluong()+"");
        viewHolderClass.tv_sotien.setText((ctddh.getDongia()*ctddh.getSoluong()+""));


    }

    @Override
    public int getItemCount() {
        return ctddhList.size();
    }
    public void setData(List<CTDDH> list,List<SanPham> sp) {
        this.ctddhList = list;
        this.products=sp;
        notifyDataSetChanged();
        Log.d("aaa", "setData:ok ");
    }
    public class ViewHolderClass extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView tv_tensp,tv_soluong,tv_sotien;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);

            imageView=(ImageView)itemView.findViewById(R.id.avartar_in_ddh);
            tv_tensp=(TextView)itemView.findViewById(R.id.name_sp_inddh);
            tv_soluong=(TextView)itemView.findViewById(R.id.soluong_sp_inddh);
            tv_sotien=(TextView)itemView.findViewById(R.id.tongtien_in_ddh);
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
    private Product layproducttutensp(String idsp, List<Product> list){

        Product p = new Product();
        for(int i=0; i<list.size(); i++){
            if(idsp.equals(list.get(i).getId())){

                p =list.get(i);
            }
        }
        return p;
    }
}

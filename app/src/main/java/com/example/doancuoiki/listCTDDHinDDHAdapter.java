package com.example.doancuoiki;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doancuoiki.Class.Product;
import com.example.doancuoiki.objectDDH.CTDDH;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class listCTDDHinDDHAdapter extends BaseAdapter {
    private List<CTDDH> listctddh;
    private List<Product> listproduct;
    Activity activity;

    public listCTDDHinDDHAdapter(List<CTDDH> listctddh, List<Product> listproduct, Activity activity){
        this.listctddh = listctddh;
        this.listproduct = listproduct;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listctddh.size();
    }

    @Override
    public Object getItem(int position) {
        return listproduct.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflare = activity.getLayoutInflater();

        convertView = inflare.inflate(R.layout.item_ctddhinddh, null);

        CTDDH ctddhtemp = new CTDDH();

        ctddhtemp = listctddh.get(position);

        Product p = new Product();

        p = layproducttutensp(ctddhtemp.getIdsp(), listproduct);


        ImageView img =(ImageView)convertView.findViewById(R.id.imgCTDDHinDDH);
        TextView tvTenSachCTDDHinDDH = (TextView)convertView.findViewById(R.id.tvTenSachCTDDHinDDH);
        TextView tvSoluongCTDDHinDDH = (TextView)convertView.findViewById(R.id.tvSoluongCTDDHinDDH);
        TextView tvGiaCTDDHinDDH = (TextView)convertView.findViewById(R.id.tvGiaCTDDHinDDH);
        int giatong  = p.getGia()*ctddhtemp.getSoluong();
//        img.setImageBitmap(chuyen_ArrByte_sang_Bitmap(p.getImg()));

        Picasso.get().load(p.getImg()).into(img);

        tvTenSachCTDDHinDDH.setText("Tên SP:"+p.getTensp());

        tvSoluongCTDDHinDDH.setText("Số lượng: "+String.valueOf(ctddhtemp.getSoluong()));
        tvGiaCTDDHinDDH.setText("Tổng giá: "+String.valueOf(giatong));


        return convertView;
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

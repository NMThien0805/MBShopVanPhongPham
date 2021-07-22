package com.example.doancuoiki.admin.adapter;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiki.R;
import com.example.doancuoiki.SanPham;
import com.example.doancuoiki.UpdatePro;
import com.example.doancuoiki.UpdateUserActivity;
import com.example.doancuoiki.Users;
import com.example.doancuoiki.admin.MainAdminActivity;
import com.example.doancuoiki.api.ApiService;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdapter extends RecyclerView.Adapter {
    Context context;
    List<SanPham> list;
    private Uri selectedUriImage;
    String picturePath;
    byte[] ImagePresent;
    int REQUEST_CODE_CAMERA=1,REQUEST_CODE_FOLDER =2;

    public ProductAdapter() {
    }
    public ProductAdapter(MainAdminActivity context) {
// Here we're getting the activity's context,
// by setting the adapter on the activity with (this)
        this.context=context;

    }
    public Context getContext() {
        return context;
    }

    public ProductAdapter(Context context, List<SanPham> list) {
        this.context = context;
        this.list = list;
    }
    public ProductAdapter(Context context) {
        this.context = context;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        /*View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);*/
        LayoutInflater layoutInflater =LayoutInflater.from(context);
        View v =layoutInflater.inflate(R.layout.item_product1,parent,false);

        return new ViewHolderClass(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass viewHolderClass = (ViewHolderClass)holder;
        SanPham sp =list.get(position);
        if(sp.getImg().isEmpty()){
            notifyDataSetChanged();
            Picasso.get().load(sp.getImg()).into(viewHolderClass.imageView);
        }else
            Picasso.get().load(sp.getImg()).into(viewHolderClass.imageView);
        viewHolderClass.tv_ten.setText(sp.getTenSP());
        viewHolderClass.tv_loai.setText(sp.getLoai()+"");
        viewHolderClass.tv_soluong.setText(sp.getSoLuong()+"");
        viewHolderClass.tv_gia.setText(sp.getGia()+"");
        viewHolderClass.btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XacNhanXoa(list.get(position));
            }
        });
        viewHolderClass.btn_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openDialogUpdate(Gravity.CENTER,list.get(position));
                Intent intent = new Intent(context, UpdatePro.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("dataSP",list.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }
    public void callAPI_Delete(SanPham sp) {
        System.out.println(sp.toString());
        Call<SanPham> res = ApiService.apiService.deleteSP(sp);
        res.enqueue(new Callback<SanPham>() {
            @Override
            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                System.out.println("đã xóa");
                list.remove(sp);
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<SanPham> call, Throwable t) {

            }
        });
    }
    private void XacNhanXoa(SanPham sp) {
        AlertDialog.Builder dialogXoa= new AlertDialog.Builder(context);
        dialogXoa.setMessage("Bạn có chắc chắn xóa sản phẩm "+sp.getTenSP()+" không ?");
        dialogXoa.setPositiveButton("có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callAPI_Delete(sp);
                dialog.dismiss();

            }
        });
        dialogXoa.setNegativeButton("không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogXoa.show();

    }

    public void openDialogUpdate(int gratity, SanPham sp) {

        final Dialog dialog =  new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_suasp);

        Window window = dialog.getWindow();
        if(window==null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAtt = window.getAttributes();
        windowAtt.gravity= gratity;
        window.setAttributes(windowAtt);

        if(Gravity.BOTTOM==gratity){
            dialog.setCancelable(true);

        }else{
            dialog.setCancelable(false);
        }

        //anhxa
        EditText edtTen=(EditText) dialog.findViewById(R.id.edt_ten_update);
        EditText edtSoluong=(EditText)dialog.findViewById(R.id.edt_soluong_update);
        EditText edtGia=(EditText)dialog.findViewById(R.id.edt_gia_update);
        EditText edtLoai=(EditText)dialog.findViewById(R.id.edt_loai_update);
        Button btn_xacnhan=(Button)dialog.findViewById(R.id.btn_xacnhan_dialogsua);
        Button btn_huy=(Button)dialog.findViewById(R.id.btn_huy_diaglogsua);
        edtTen.setText(sp.getTenSP());
        edtSoluong.setText(sp.getSoLuong()+"");
        edtLoai.setText(sp.getLoai()+"");
        edtGia.setText(sp.getGia()+"");
        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten= edtTen.getText().toString();
                int soluong= Integer.parseInt(edtSoluong.getText().toString().trim());
                String loai=(edtLoai.getText().toString().trim());
                int gia= Integer.parseInt(edtGia.getText().toString().trim());
                sp.setTenSP(ten);
                sp.setGia(gia);
                sp.setLoai(loai);
                sp.setSoLuong(soluong);
                notifyDataSetChanged();
                Toast.makeText(getContext(), "đã update", Toast.LENGTH_SHORT).show();
                //productAdapter.setData(list1);
                dialog.dismiss();

            }
        });

        dialog.show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void setData(List<SanPham> list){
        this.list=list;
        notifyDataSetChanged();
        Log.d("aaa", "setData:ok ");
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView tv_ten,tv_gia,tv_soluong,tv_loai;
        Button btn_xoa, btn_sua;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);

            btn_xoa=(Button)itemView.findViewById(R.id.btn_xoa);
            btn_sua = (Button)itemView.findViewById(R.id.btn_sua);
            imageView=(ImageView)itemView.findViewById(R.id.img_item_pro);
            tv_ten=(TextView)itemView.findViewById(R.id.ten_item_pro);
            tv_gia=(TextView)itemView.findViewById(R.id.gia_item_pro);
            tv_soluong=(TextView)itemView.findViewById(R.id.soluong_item_pro);
            tv_loai=(TextView)itemView.findViewById(R.id.loai_item_pro);
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
    private String getPicturePath(Uri uriImage) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContext().getContentResolver().query(uriImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String path = cursor.getString(columnIndex);
        cursor.close();
        return path;
    }
}

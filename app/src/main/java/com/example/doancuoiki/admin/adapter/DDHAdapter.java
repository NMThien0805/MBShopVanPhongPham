package com.example.doancuoiki.admin.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiki.CTinDHActivity;
import com.example.doancuoiki.DDH;
import com.example.doancuoiki.R;
import com.example.doancuoiki.SanPham;
import com.example.doancuoiki.TT_CTDDH_SP;
import com.example.doancuoiki.api.ApiService;
import com.example.doancuoiki.objectDDH.CTDDH;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DDHAdapter extends RecyclerView.Adapter{
    Context context;
    List<DDH> list;
    TT_CTDDH_SP tam= new TT_CTDDH_SP();
    public Context getContext() {
        return context;
    }

    public DDHAdapter(Context context, List<DDH> list) {
        this.context = context;
        this.list = list;
    }
    public DDHAdapter(Context context) {
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(context);
        View v =layoutInflater.inflate(R.layout.item_ddh,parent,false);

        return new ViewHolderClass(v);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DDHAdapter.ViewHolderClass viewHolderClass = (DDHAdapter.ViewHolderClass)holder;
        DDH ddh =list.get(position);



        viewHolderClass.tv_userid.setText(ddh.getUserID()+"");
        viewHolderClass.tv_ngaydat.setText(ddh.getNgaydat());
        viewHolderClass.tv_TT.setText(ddh.getTrangThai());
        if (ddh.getTrangThai().matches("DXN")){
            viewHolderClass.btn_sua.setVisibility(View.INVISIBLE);
        }
        //viewHolderClass.tv_maddh.setText(ddh.getMaDDH()+"");


        viewHolderClass.btn_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // openDialogUpdate(Gravity.CENTER,list.get(position));
                CallAPI_XacNhan(list.get(position));
                viewHolderClass.btn_sua.setVisibility(View.INVISIBLE);


            }
        });
        viewHolderClass.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(context, CTinDHActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("dataDDH",list.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
                //CallAPI(list.get(position));
               // openDialogUpdate(Gravity.CENTER,tam.getListsp(),tam.getCtddhList());

                return false;
            }
        });

    }
    private void CallAPI_XacNhan(DDH ddh) {
//        System.out.println(ddh.toString());
       Call<DDH> listCall = ApiService.apiService.xacnhan(ddh);
       listCall.enqueue(new Callback<DDH>() {
           @Override
           public void onResponse(Call<DDH> call, Response<DDH> response) {
               System.out.println("Thành công");
               ddh.setTrangThai("DXN");
               notifyDataSetChanged();
           }

           @Override
           public void onFailure(Call<DDH> call, Throwable t) {

           }
       });



    }
//    private void openDialogUpdate(int gratity, List<SanPham> sps, List<CTDDH> ctddhs) {
//        final Dialog dialog =  new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_suaddh);
//
//        Window window = dialog.getWindow();
//        if(window==null){
//            return;
//        }
//
//        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
//        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        WindowManager.LayoutParams windowAtt = window.getAttributes();
//        windowAtt.gravity= gratity;
//        window.setAttributes(windowAtt);
//
//        if(Gravity.BOTTOM==gratity){
//            dialog.setCancelable(true);
//
//        }else{
//            dialog.setCancelable(false);
//        }
//
////        //anhxa
//        RecyclerView recyclerView = (RecyclerView)dialog.findViewById(R.id.rv_listSPinDDH);
////        EditText edtmaddh=(EditText) dialog.findViewById(R.id.edt_ma_ddh);
////        EditText edtngay=(EditText)dialog.findViewById(R.id.edt_ngaydathang);
////        EditText edtuserid=(EditText)dialog.findViewById(R.id.edt_userid);
////
////        Button btn_xacnhan=(Button)dialog.findViewById(R.id.btn_xacnhan_ddh);
////        Button btn_huy=(Button)dialog.findViewById(R.id.btn_huy_ddh);
//        //edtmaddh.setText(ddh.getMaDDH()+"");
//       // edtngay.setText(ddh.getNgayDat());
//        //edtuserid.setText(ddh.getUserID()+"");
////        btn_huy.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                dialog.dismiss();
////            }
////        });
////        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////                int madddh= Integer.parseInt(edtmaddh.getText().toString().trim());
////                String ngay= edtngay.getText().toString().trim();
////                String userid= (edtuserid.getText().toString().trim());
////               // ddh.setMaDDH(madddh);
////               // ddh.setNgayDat(ngay);
////                ddh.setUserID(userid);
////                notifyDataSetChanged();
////                Toast.makeText(getContext(), "đã update", Toast.LENGTH_SHORT).show();
////                //productAdapter.setData(list1);
////                dialog.dismiss();
////
////            }
////        });
//
//        dialog.show();
//    }
//    private void openDialogct(int gratity, DDH ddh) {
//        final Dialog dialog =  new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_suaddh);
//
//        Window window = dialog.getWindow();
//        if(window==null){
//            return;
//        }
//
//        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
//        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        WindowManager.LayoutParams windowAtt = window.getAttributes();
//        windowAtt.gravity= gratity;
//        window.setAttributes(windowAtt);
//
//        if(Gravity.BOTTOM==gratity){
//            dialog.setCancelable(true);
//
//        }else{
//            dialog.setCancelable(false);
//        }
//
//        //anhxa
//        EditText edtmaddh=(EditText) dialog.findViewById(R.id.edt_ma_ddh);
//        EditText edtngay=(EditText)dialog.findViewById(R.id.edt_ngaydathang);
//        EditText edtuserid=(EditText)dialog.findViewById(R.id.edt_userid);
//
//        Button btn_xacnhan=(Button)dialog.findViewById(R.id.btn_xacnhan_ddh);
//        Button btn_huy=(Button)dialog.findViewById(R.id.btn_huy_ddh);
//        //edtmaddh.setText(ddh.getMaDDH()+"");
//        //edtngay.setText(ddh.getNgayDat());
//        edtuserid.setText(ddh.getUserID()+"");
//        btn_huy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                int madddh= Integer.parseInt(edtmaddh.getText().toString().trim());
//                String ngay= edtngay.getText().toString().trim();
//                String userid= (edtuserid.getText().toString().trim());
//                //ddh.setMaDDH(madddh);
//                //ddh.setNgayDat(ngay);
//                ddh.setUserID(userid);
//                notifyDataSetChanged();
//                Toast.makeText(getContext(), "đã update", Toast.LENGTH_SHORT).show();
//                //productAdapter.setData(list1);
//                dialog.dismiss();
//
//            }
//        });
//
//        dialog.show();
//    }

    private void XacNhanXoa(DDH ddh) {
        AlertDialog.Builder dialogXoa= new AlertDialog.Builder(context);
        //dialogXoa.setMessage("Bạn có chắc chắn xóa đơn đặt hàng "+ddh.getMaDDH()+" không ?");
        dialogXoa.setPositiveButton("có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                list.remove(ddh);
                notifyDataSetChanged();
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

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void setData(List<DDH> list){
        this.list=list;
        notifyDataSetChanged();
        Log.d("aaa", "setData:ok ");
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{

        TextView tv_maddh,tv_ngaydat,tv_userid,tv_TT;
        Button btn_xoa, btn_sua,btn_ctddh;
        LinearLayout linearLayout;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);

            //btn_xoa=(Button)itemView.findViewById(R.id.btn_xoa_ddh);
            btn_sua = (Button)itemView.findViewById(R.id.btn_sua_ddh);



            //tv_maddh=(TextView)itemView.findViewById(R.id.maddh);
            tv_ngaydat=(TextView)itemView.findViewById(R.id.ngaydat);
            tv_userid=(TextView)itemView.findViewById(R.id.iduser);
            tv_TT=(TextView)itemView.findViewById(R.id.ttddh);
            linearLayout= (LinearLayout) itemView.findViewById(R.id.ctddh);



        }
    }
}

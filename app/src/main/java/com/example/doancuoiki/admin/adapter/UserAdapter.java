package com.example.doancuoiki.admin.adapter;

import android.app.Activity;
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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiki.MainActivity;
import com.example.doancuoiki.R;
import com.example.doancuoiki.SanPham;
import com.example.doancuoiki.UpdateUserActivity;
import com.example.doancuoiki.Users;
import com.example.doancuoiki.admin.MainAdminActivity;
import com.example.doancuoiki.admin.fragment.UserFragment;
import com.example.doancuoiki.api.ApiService;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class UserAdapter extends RecyclerView.Adapter {
    Activity context;
    List<Users> list;
    private Uri selectedUriImage;
    String picturePath;
    String ImagePresent;
    int REQUEST_CODE_CAMERA=1,REQUEST_CODE_FOLDER =2;
    ImageView imgView;
    Users users;


    public UserAdapter() {
    }

    public Activity getContext() {
        return context;
    }

    public UserAdapter(Activity context, List<Users> list) {
        this.context = context;
        this.list = list;
    }
    public UserAdapter(Activity context) {
        this.context = context;

    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        /*View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);*/
        LayoutInflater layoutInflater =LayoutInflater.from(getContext());
        View v =layoutInflater.inflate(R.layout.item_user,parent,false);


        return new ViewHolderClass(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass viewHolderClass = (ViewHolderClass)holder;
        Users user =list.get(position);
        String phai;
        if(user.getImg().isEmpty()){

            notifyDataSetChanged();
            //viewHolderClass.imageView.setImageResource(R.drawable.girl);
            Picasso.get().load(user.getImg()).into(viewHolderClass.imageView);

        }else
            Picasso.get().load(user.getImg()).into(viewHolderClass.imageView);
        viewHolderClass.tv_ten.setText(user.getName());
        if(user.isGioitinh()){
            phai="nam";
        }else
            phai="nữ";
        viewHolderClass.tv_gioitinh.setText(phai);
        viewHolderClass.tv_email.setText(user.getMail());
        viewHolderClass.tv_sdt.setText(user.getSdt());
        viewHolderClass.tv_diachi.setText(user.getDiachi());
        viewHolderClass.tv_role.setText(user.getRole());
        viewHolderClass.btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XacNhanXoa(list.get(position));
            }
        });
        viewHolderClass.btn_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateUserActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("dataUser",list.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
                //context.penDialogSua(Gravity.CENTER,list.get(position));
                //context.openDialog(Gravity.CENTER,list.get(position));
                //context.openDialog(Gravity.CENTER,list.get(position));

            }
        });

    }

    public void callAPI_Delete(Users user) {
        System.out.println(user.toString());
        Call<Users> res = ApiService.apiService.delete(user);
        res.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                System.out.println("ok");
                list.remove(user);
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                System.out.println("lỗi");
            }
        });
    } public void callAPI_update(Users user) {
        System.out.println(user.toString());
        Call<Users> res = ApiService.apiService.putEdit(user);
        res.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                System.out.println("ok");
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                System.out.println("lỗi");
            }
        });
    }
    private void XacNhanXoa(Users users) {
        AlertDialog.Builder dialogXoa= new AlertDialog.Builder(context);
        dialogXoa.setMessage("Bạn có chắc chắn xóa user "+users.getName()+" không ?");
        dialogXoa.setPositiveButton("có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callAPI_Delete(users);

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

    public void openDialog(int gratity, Users user) {

        final Dialog dialog =  new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sua_user);

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
        EditText edtTen=(EditText) dialog.findViewById(R.id.edt_ten_user_diaglogsua);
        EditText edtusername=(EditText) dialog.findViewById(R.id.edt_username_dialogsua);
        EditText edtpassword=(EditText) dialog.findViewById(R.id.edt_password_dialogsua);
        EditText edtemail=(EditText) dialog.findViewById(R.id.edt_mail_dialogsua);
        EditText edtsdt=(EditText) dialog.findViewById(R.id.edt_sdt_dialogsua);
        EditText edtdiachi=(EditText) dialog.findViewById(R.id.edt_diachi_dialogsua);
        RadioButton rbnam=(RadioButton)dialog.findViewById(R.id.rb_nam_dialogsua);
        RadioButton rbnu=(RadioButton)dialog.findViewById(R.id.rb_nu_dialogsua);
        RadioButton rbadmin=(RadioButton)dialog.findViewById(R.id.rb_admin_dialogsua);
        RadioButton rbuser=(RadioButton)dialog.findViewById(R.id.rb_user_dialogsua);

        Button btn_xacnhan=(Button)dialog.findViewById(R.id.btn_xacnhan_user_dialogsua);
        Button btn_huy=(Button)dialog.findViewById(R.id.btn_huy_user_dialogsua);
        Button btn_Folder = (Button)dialog.findViewById(R.id.btn_folder_user_dialogsua);
        Button btn_Camera = (Button)dialog.findViewById(R.id.btn_camera_user_dialogsua);
        imgView = (ImageView)dialog.findViewById(R.id.img_dia_avatar_user_dialogsua);


        edtTen.setText(user.getName());
        edtusername.setText(user.getUsername());
        edtpassword.setText(user.getPassword());
        edtdiachi.setText(user.getDiachi());
        edtemail.setText(user.getMail());
        edtsdt.setText(user.getSdt());
        if(user.isGioitinh())
            rbnam.setChecked(true);
        else rbnu.setChecked(true);
        if (user.getRole()=="admin")
            rbadmin.setChecked(true);
        else rbuser.setChecked(true);

//        imgView.setImageBitmap(chuyen_ArrByte_sang_Bitmap(user.getImg()));
        ImagePresent = user.getImg();
        btn_Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                ((Activity)context).startActivityForResult(intent,REQUEST_CODE_CAMERA);
            }
        });
        btn_Folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                ((Activity)context).startActivityForResult(intent,REQUEST_CODE_FOLDER);
            }
        });

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten= edtTen.getText().toString().trim();
                String username=edtusername.getText().toString().trim();
                String password=edtpassword.getText().toString().trim();
                String email=edtemail.getText().toString().trim();
                String sdt=edtsdt.getText().toString().trim();
                String diachi=edtdiachi.getText().toString().trim();
                boolean phai;
                String role;
                if(rbnam.isChecked())
                    phai=true;
                else
                    phai=false;
                if(rbadmin.isChecked())
                    role="admin";
                else
                    role="user";

//                byte[] resize = chuyen_Bitmap_sang_ArrByte(getResizedBitmap(chuyen_ArrByte_sang_Bitmap(ImagePresent), 100, 100));

                Users users = new Users(ten,username,password,phai,email,diachi,sdt,role,selectedUriImage.toString(),"");
                callAPI_update(users);
                user.setName(ten);
                user.setDiachi(diachi);
                user.setMail(email);
                user.setUsername(username);
                user.setPassword(password);
                user.setRole(role);
                user.setGioitinh(phai);
                user.setSdt(sdt);
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
    public void setData(List<Users> list){
        this.list=list;
        notifyDataSetChanged();
        Log.d("aaa", "setData:ok ");
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView tv_ten,tv_gioitinh,tv_email,tv_sdt,tv_diachi,tv_role;
        Button btn_xoa, btn_sua;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);

            btn_xoa=(Button)itemView.findViewById(R.id.btn_xoa_user);
            btn_sua = (Button)itemView.findViewById(R.id.btn_sua_user);
            imageView=(ImageView)itemView.findViewById(R.id.img_item_user);
            tv_ten=(TextView)itemView.findViewById(R.id.ten_item_user);
            tv_gioitinh=(TextView)itemView.findViewById(R.id.gioitinh_item_user);
            tv_email=(TextView)itemView.findViewById(R.id.email_item_user);
            tv_sdt=(TextView)itemView.findViewById(R.id.sdt_item_user);
            tv_diachi=(TextView)itemView.findViewById(R.id.diachi_item_user);
            tv_role=(TextView)itemView.findViewById(R.id.role_item_user);
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

package com.example.doancuoiki;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.doancuoiki.api.ApiService;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUserActivity extends AppCompatActivity {
    EditText edtTen ,edtusername,edtpassword,edtemail,edtsdt,edtdiachi;
    RadioButton rbnam, rbnu, rbadmin, rbuser;
    Button btn_xacnhan, btn_huy, btn_Folder,btn_Camera;
    ImageView avatar;
    private Uri selectedUriImage;
    String picturePath;
    byte[] ImagePresent;
    int REQUEST_CODE_CAMERA=1,REQUEST_CODE_FOLDER =2;
    Users user=null;
    //byte[] resize= null;
    Users user2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            user=(Users) bundle.getSerializable("dataUser");
        }

        AnhXa();
        Picasso.get().load(user.getImg()).into(avatar);
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

//        avatar.setImageBitmap(chuyen_ArrByte_sang_Bitmap(user.getImg()));
//        ImagePresent = user.getImg();
        btn_Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_CODE_CAMERA);
            }
        });
        btn_Folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_FOLDER);
            }
        });
        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten= edtTen.getText().toString();
                String username= edtusername.getText().toString();
                String password= edtpassword.getText().toString();
                String mail= edtemail.getText().toString();
                String sdt= edtsdt.getText().toString();
                String diachi= edtdiachi.getText().toString();
                Boolean phai;
                String role;
                if(rbnam.isChecked())
                    phai=true;
                else
                    phai=false;

                if(rbadmin.isChecked())
                    role="admin";
                else role="user";

                byte[] resize = chuyen_Bitmap_sang_ArrByte(getResizedBitmap(chuyen_ArrByte_sang_Bitmap(ImagePresent), 100, 100));

                user2 = new Users(ten,username,password,phai,mail,diachi,sdt,role,selectedUriImage.toString(),"");

                callAPI_update(user2);
                finish();

                /*list1.clear();
                CallAPI();
                recyclerView.setAdapter(userAdapter);*/
//                list1.add(users);
//                userAdapter.setData(list1);
            }
        });
        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void callAPI_update(Users user) {
        System.out.println(user.toString());
        ContentResolver cR = getContentResolver();
        UploadImgFBase fb = new UploadImgFBase();
        fb.set_for_user(user, selectedUriImage, cR, this, 2);
        fb.actionUpload();
    }
    public void callAPI_Register(Users user) {
        System.out.println(user.toString());
        Call<Users> res = ApiService.apiService.register(user);
        res.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {

                Toast.makeText(UpdateUserActivity.this, "RESULT: " + response.code(), Toast.LENGTH_SHORT).show();
                System.out.println(response.code());
                finish();
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(UpdateUserActivity.this, "Call API Regiter Error", Toast.LENGTH_SHORT).show();
                Log.d("false", "sai ");
            }
        });
    }



    private void AnhXa() {
        edtTen=(EditText) findViewById(R.id.edt_ten_user_diaglogsua);
        edtusername=(EditText) findViewById(R.id.edt_username_dialogsua);
        edtpassword=(EditText) findViewById(R.id.edt_password_dialogsua);
        edtemail=(EditText)findViewById(R.id.edt_mail_dialogsua);
        edtsdt=(EditText) findViewById(R.id.edt_sdt_dialogsua);
        edtdiachi=(EditText) findViewById(R.id.edt_diachi_dialogsua);
        rbnam=(RadioButton)findViewById(R.id.rb_nam_dialogsua);
        rbnu=(RadioButton)findViewById(R.id.rb_nu_dialogsua);
        rbadmin=(RadioButton)findViewById(R.id.rb_admin_dialogsua);
        rbuser=(RadioButton)findViewById(R.id.rb_user_dialogsua);

        btn_xacnhan=(Button)findViewById(R.id.btn_xacnhan_user_dialogsua);
        btn_huy=(Button)findViewById(R.id.btn_huy_user_dialogsua);
        btn_Folder = (Button)findViewById(R.id.btn_folder_user_dialogsua);
        btn_Camera = (Button)findViewById(R.id.btn_camera_user_dialogsua);
        avatar = (ImageView)findViewById(R.id.img_dia_avatar_user_dialogsua);

    }
    private String getPicturePath(Uri uriImage) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uriImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String path = cursor.getString(columnIndex);
        cursor.close();
        return path;
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            //Lấy URI hình kết quả trả về
            selectedUriImage = data.getData();
//            System.out.println(selectedUriImage);
            //lấy đường dẫn hình
            picturePath=getPicturePath(selectedUriImage);
//            System.out.println(picturePath);
            InputStream inputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(selectedUriImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream);
            avatar.setImageBitmap(selectedBitmap);
            ImagePresent = chuyen_Bitmap_sang_ArrByte(selectedBitmap);

        }
        else if(requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            avatar.setImageBitmap(bitmap);
            ImagePresent = chuyen_Bitmap_sang_ArrByte(bitmap);
        }
    }
}
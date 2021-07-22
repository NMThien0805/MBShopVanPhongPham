package com.example.doancuoiki;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Header;

import com.example.doancuoiki.admin.MainAdminActivity;
import com.example.doancuoiki.api.ApiService;
import com.example.doancuoiki.objectDDH.DonDatHang;
import com.example.doancuoiki.objectDDH.resDDH;
import com.example.doancuoiki.objectDDH.truyenDDH;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText edtUsername, edtPassword, edtName, edtmail, edtDiachi, edtSDT;
    Button btnDangNhap, btnDangKy, btnThoat;
    String tdn, mk;
    private Uri selectedUriImage;
    String picturePath;
    byte[] ImagePresent;
    List<Users> usersList=new ArrayList<>();
    Users user=new Users();
    ImageView imgView;
    TextView forgotPass, confirmforgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setControl();
        setEvent();
    }

    public void callAPI_Register(Users user) {
//        System.out.println(user.toString());
        ContentResolver cR = getContentResolver();
        UploadImgFBase fb = new UploadImgFBase();
        fb.set_for_user(user, selectedUriImage, cR, this, 1);
        fb.actionUpload();

//        Call<Users> res = ApiService.apiService.register(user);
//        res.enqueue(new Callback<Users>() {
//            @Override
//            public void onResponse(Call<Users> call, Response<Users> response) {
////                Toast.makeText(MainActivity.this, "RESULT: " + response.body(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<Users> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "Call API Regiter Error", Toast.LENGTH_SHORT).show();
//                Log.d("false", "sai ");
//            }
//        });
    }

    public void callAPI_Login(String username, String password) {
//        Toast.makeText(this, "aksbdqwiqwvdqhwq", Toast.LENGTH_LONG).show();
//        Toast.makeText(this, "Vao API login", Toast.LENGTH_LONG).show();

        ObjectLogin input = new ObjectLogin(username, password);

        //System.out.println(input);

        Call<UserLogged> result = ApiService.apiService.login(input);
        result.enqueue(new Callback<UserLogged>() {
            @Override
            public void onResponse(Call<UserLogged> call, Response<UserLogged> response) {
                //Toast.makeText(MainActivity.this, "token:" + response.body().toString(), Toast.LENGTH_LONG).show();
                System.out.println("token: " + response.toString());

                String role = "";
                UserLogged usertam = new UserLogged();
                if(response.code() == 200){
                    role = response.body().getRole();
                    usertam=response.body();
                }
                else{
                    role = "Pass or username wrong";
                }
                if(role.trim().equals("admin")){
                    Intent intent  = new Intent(MainActivity.this,MainAdminActivity.class);
                    Bundle bundle  = new Bundle();
                    bundle.putSerializable("object_user",usertam);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else if(role.trim().equals("user")){
                    Intent intent  = new Intent(MainActivity.this,Home.class);
                    Bundle bundle  = new Bundle();
                    bundle.putSerializable("object_user",usertam);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else Toast.makeText(MainActivity.this, "Username or password is wrong!!!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<UserLogged> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Call API Login Error", Toast.LENGTH_SHORT).show();
                Log.d("false", "sai ");
            }
        });
    }

    private void setControl(){
        edtUsername = (EditText)findViewById(R.id.edtUsername);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        btnDangNhap = (Button)findViewById(R.id.btnDangNhap);
        btnDangKy = (Button)findViewById(R.id.btnDangKy);
        btnThoat = (Button)findViewById(R.id.btnThoat);
        forgotPass = (TextView)findViewById(R.id.tvQuenMK);
        confirmforgot = (TextView)findViewById(R.id.tvXacNhanQuenMK);
    }

    private void setEvent(){
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuenMKActivity.class);
                startActivity(intent);
            }
        });
        confirmforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, XacNhanQuenMkActivity.class);
                startActivity(intent);
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_DeviceDefault_Dialog);
                b.setTitle("Bạn chắc chắn muốn thoát chứ?");
                b.setMessage("Mời bạn lựa chọn");
                b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                    }
                });
                b.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                b.show();
            }
        });

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setTitle("Đăng Ký Tài Khoản Mới");
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_dangky);
                EditText edtuserDK = (EditText)dialog.findViewById(R.id.edtUsernameDK);
                EditText edtpassDK = (EditText)dialog.findViewById(R.id.edtPasswordDK);
                EditText edtName = (EditText)dialog.findViewById(R.id.edtName);
                EditText edtmail = (EditText)dialog.findViewById(R.id.edtmail);
                EditText edtDiachi = (EditText)dialog.findViewById(R.id.edtDiachi);
                EditText edtSDT = (EditText)dialog.findViewById(R.id.edtSDT);
                Button btnDKDialog = (Button)dialog.findViewById(R.id.btnDangKyDialog);
                Button btnBack = (Button)dialog.findViewById(R.id.btnBack);
                Button btnDangKyDialog = (Button)dialog.findViewById(R.id.btnDangKyDialog);
                imgView = (ImageView) dialog.findViewById(R.id.imgView);
                Button btnFolder = (Button)dialog.findViewById(R.id.btnFolder);
                Button btnCamera = (Button)dialog.findViewById(R.id.btnCamera);

                btnDangKyDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        byte[] resize = chuyen_Bitmap_sang_ArrByte(getResizedBitmap(chuyen_ArrByte_sang_Bitmap(ImagePresent), 100, 100));

                        Users user = new Users(edtName.getText().toString(),
                                edtuserDK.getText().toString(),
                                edtpassDK.getText().toString(),
                                true,
                                edtmail.getText().toString(),
                                edtDiachi.getText().toString(),
                                edtSDT.getText().toString(),
                                "user",
                                selectedUriImage.toString());
                        callAPI_Register(user);
                        dialog.cancel();
                    }
                });
                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
                btnCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 200);
                    }
                });
                btnFolder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent,100);
                    }
                });
            }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Vao API login", Toast.LENGTH_LONG).show();
                callAPI_Login(edtUsername.getText().toString().trim(), edtPassword.getText().toString().trim());
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
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
            imgView.setImageBitmap(selectedBitmap);
            ImagePresent = chuyen_Bitmap_sang_ArrByte(selectedBitmap);
        }
        else if(requestCode == 200 && resultCode == RESULT_OK && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgView.setImageBitmap(bitmap);
            ImagePresent = chuyen_Bitmap_sang_ArrByte(bitmap);
        }
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
}
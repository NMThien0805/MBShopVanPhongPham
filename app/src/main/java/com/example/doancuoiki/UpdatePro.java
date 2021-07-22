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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePro extends AppCompatActivity {
    EditText edtTen,edtSoluong,edtGia, edtLoai;
    Button btn_xacnhan,btn_huy,btn_Folder,btn_Camera;
    ImageView avatar;
    RadioButton rb1,rb2 ,rb3;
    private Uri selectedUriImage;
    String picturePath;
    String ImagePresent;
    int REQUEST_CODE_CAMERA=1,REQUEST_CODE_FOLDER =2;
    SanPham sanPham=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pro);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            sanPham=(SanPham) bundle.getSerializable("dataSP");
        }
        AnhXa();
        edtTen.setText(sanPham.getTenSP());
        edtSoluong.setText(sanPham.getSoLuong()+"");
        //edtLoai.setText(sanPham.getLoai()+"");
        String loai=sanPham.getLoai();
        if(loai.matches("Loai 1")){
            rb1.setChecked(true);
        }else if (loai.matches("Loai 2")){
            rb2.setChecked(true);
        }else rb3.setChecked(true);

        edtGia.setText(sanPham.getGia()+"");
       //avatar.setImageBitmap(chuyen_ArrByte_sang_Bitmap(sanPham.getImg()));
        Picasso.get().load(sanPham.getImg()).into(avatar);
        ImagePresent = sanPham.getImg();

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
                int soluong= Integer.parseInt(edtSoluong.getText().toString().trim());
                //String loai=(edtLoai.getText().toString().trim());
                String loai;
                if(rb1.isChecked()){
                    loai="Loai 1";
                }else if(rb2.isChecked()){
                    loai="Loai 2";
                }else
                    loai= "Loai 3";
                int gia= Integer.parseInt(edtGia.getText().toString().trim());
//                byte[] resize = chuyen_Bitmap_sang_ArrByte(getResizedBitmap(chuyen_ArrByte_sang_Bitmap(ImagePresent), 100, 100));


                SanPham sp = new SanPham(ten,soluong,loai,gia,selectedUriImage.toString());
                callAPI_update(sp);
                finish();

            }
        });
        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void callAPI_update(SanPham sp) {
        System.out.println(sp.toString());
        //Call<SanPham> res = ApiService.apiService.putEditPro(sp);
        ContentResolver cR = getContentResolver();
//        SanPham sp = new SanPham("Sach cua Bua", 100, "Loai 1", 100000, "");
        UploadImgFBase fb = new UploadImgFBase();
        fb.set_for_product(sp, selectedUriImage, cR, this, 2);
        fb.actionUpload();
    }
    private void AnhXa() {
        edtTen=(EditText) findViewById(R.id.edt_ten_update);
        edtSoluong=(EditText)findViewById(R.id.edt_soluong_update);
        edtGia=(EditText)findViewById(R.id.edt_gia_update);
        //edtLoai=(EditText)findViewById(R.id.edt_loai_update);
        btn_xacnhan=(Button)findViewById(R.id.btn_xacnhan_dialogsua);
        btn_huy=(Button)findViewById(R.id.btn_huy_diaglogsua);
        btn_Folder = (Button)findViewById(R.id.btn_folder_update);
        btn_Camera = (Button)findViewById(R.id.btn_camera_update);
        avatar = (ImageView)findViewById(R.id.img_dia_avatar_update);
         rb1= (RadioButton)findViewById(R.id.rbLoai1) ;

        rb2= (RadioButton)findViewById(R.id.rbLoai2) ;
        rb3= (RadioButton)findViewById(R.id.rbLoai3) ;
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
//            ImagePresent = chuyen_Bitmap_sang_ArrByte(selectedBitmap);
            //ImagePresent = chuyen_Bitmap_sang_ArrByte(getResizedBitmap(selectedBitmap, 100, 100));

        }
        else if(requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            avatar.setImageBitmap(bitmap);
            //ImagePresent = chuyen_Bitmap_sang_ArrByte(getResizedBitmap(bitmap, 100, 100));
//            ImagePresent = chuyen_Bitmap_sang_ArrByte(bitmap);
        }
    }
}
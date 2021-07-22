package com.example.doancuoiki.admin.fragment;


import android.app.Application;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiki.Home;
import com.example.doancuoiki.MyApplication;
import com.example.doancuoiki.R;
import com.example.doancuoiki.SanPham;
import com.example.doancuoiki.UploadImgFBase;
import com.example.doancuoiki.admin.adapter.ProductAdapter;
import com.example.doancuoiki.api.ApiService;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductFragment extends Fragment implements Animation.AnimationListener {
    View v;
    ProductAdapter productAdapter;
    RecyclerView recyclerView;
    ImageView btn_Refresh,btn_An;
    Boolean an=true;
    List<SanPham> list1 =new ArrayList<>();
    SanPham sp;
    Button btn_Them;
    ImageView avatar;
    private Uri selectedUriImage;
    String picturePath;
    byte[] ImagePresent;
    int REQUEST_CODE_CAMERA=1,REQUEST_CODE_FOLDER =2;

    Animation FadeIn,SlideDown,Blink,Move,MoveNguoc;

    private static final int NOTIFICATION_ID = 1;
    private CharSequence Message_Notifycation="";





    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductFragment newInstance(String param1, String param2) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //list1 = new ArrayList<>();
        //list1.add(new SanPham("Trâu1",1000,1,"anh",1000));
        //getListSP(list1);
        //recyclerView.setAdapter(productAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_product1, container, false);

        recyclerView=(RecyclerView)v.findViewById(R.id.rcv_listSP);
        if(productAdapter!=null){
            recyclerView.setAdapter(productAdapter);
        }
        btn_Them=(Button)v.findViewById(R.id.btn_them_pro_frag) ;
        btn_Them.setVisibility(View.INVISIBLE);
        btn_An=(ImageView)v.findViewById(R.id.btn_am_pro_frag) ;
        btn_Refresh=(ImageView)v.findViewById(R.id.btn_refesh_pro_frag) ;
        btn_Refresh.setVisibility(View.INVISIBLE);
        FadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fadein);
        FadeIn.setAnimationListener(this);
        SlideDown=AnimationUtils.loadAnimation(getContext(), R.anim.slidedown);
        SlideDown.setAnimationListener(this);
        Move=AnimationUtils.loadAnimation(getContext(), R.anim.movex);
        Move.setAnimationListener(this);
        MoveNguoc=AnimationUtils.loadAnimation(getContext(), R.anim.movexnguoc);
        MoveNguoc.setAnimationListener(this);
        recyclerView.setAnimation(SlideDown);
        //btn_Refresh.setAnimation(SlideDown);
        //btn_Them.setAnimation(MoveNguoc);
        btn_An.setAnimation(MoveNguoc);
        btn_Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(Gravity.CENTER);

            }
        });
        btn_Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list1.clear();
                CallAPI();
                recyclerView.setAdapter(productAdapter);
            }
        });
        btn_An.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(an){
                    btn_Refresh.setVisibility(View.VISIBLE);
                    btn_Them.setVisibility(View.VISIBLE);
                    an=false;
                }
                else {
                    btn_Refresh.setVisibility(View.INVISIBLE);
                    btn_Them.setVisibility(View.INVISIBLE);
                    an=true;
                }
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        productAdapter = new ProductAdapter(getContext(),list1);
        CallAPI();

        recyclerView.setAdapter(productAdapter);


        return v;
    }

    private void CallAPI() {
        Call<List<SanPham>> listCall = ApiService.apiService.getAllPruduct();
        listCall.enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                List<SanPham> list3= new ArrayList<>();
                list3 = response.body();
                for(int i=0;i<list3.size();i++) {
                    list1.add(list3.get(i));

                }
                System.out.println(response.body());
                productAdapter.setData(list1);

            }
            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {
                Toast.makeText(getContext(), "Call API Error", Toast.LENGTH_SHORT).show();
                Log.d("false", t.toString());
            }
        });

    }
    public void callAPI_Register(SanPham sp) {
//        System.out.println(sp.toString());

        ContentResolver cR = getContext().getContentResolver();
//        SanPham sp = new SanPham("Sach cua Bua", 100, "Loai 1", 100000, "");
        UploadImgFBase fb = new UploadImgFBase();
        fb.set_for_product(sp, selectedUriImage, cR, getContext(), 1);
        fb.actionUpload();
    }

    private void openDialog(int gratity) {
        final Dialog dialog =  new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_themsp);

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
        Button btnFolder = (Button)dialog.findViewById(R.id.btn_folder);
        Button btnCamera = (Button)dialog.findViewById(R.id.btn_camera);
        avatar = (ImageView) dialog.findViewById(R.id.img_dia_avatar);
        EditText edtTen=(EditText) dialog.findViewById(R.id.edt_ten);
        EditText edtSoluong=(EditText)dialog.findViewById(R.id.edt_soluong);
        EditText edtGia=(EditText)dialog.findViewById(R.id.edt_gia);
        //EditText edtLoai=(EditText)dialog.findViewById(R.id.edt_loai);
        RadioButton rb1= (RadioButton)dialog.findViewById(R.id.rbLoai1Them) ;
        rb1.setChecked(true);
        RadioButton rb2= (RadioButton)dialog.findViewById(R.id.rbLoai2Them) ;
        RadioButton rb3= (RadioButton)dialog.findViewById(R.id.rbLoai3Them) ;
        Button btn_xacnhan=(Button)dialog.findViewById(R.id.btn_xacnhan_dialogthem);
        Button btn_huy=(Button)dialog.findViewById(R.id.btn_huy_diaglog);
        btnFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_FOLDER);
            }
        });
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_CODE_CAMERA);
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
                if(edtTen.getText().toString().isEmpty() || edtGia.getText().toString().isEmpty() ||edtSoluong.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "nhập đủ thông tin vào", Toast.LENGTH_SHORT).show();
                }
                else {
                    String ten= edtTen.getText().toString();
                    int soluong= Integer.parseInt(edtSoluong.getText().toString().trim());
                    String loai;
                    if(rb1.isChecked()){
                        loai="Loai 1";
                    }else if(rb2.isChecked()){
                        loai="Loai 2";
                    }else
                        loai= "Loai 3";

                    int gia= Integer.parseInt(edtGia.getText().toString().trim());
                    byte[] resize = chuyen_Bitmap_sang_ArrByte(getResizedBitmap(chuyen_ArrByte_sang_Bitmap(ImagePresent), 100, 100));
                    //list1.add(new SanPham(ten,soluong,loai,"",gia));
                    sp = new SanPham(ten,soluong,loai,gia,"");
                    callAPI_Register(sp);
                    list1.add(sp);
                    Toast.makeText(getContext(), "đã thêm", Toast.LENGTH_SHORT).show();
                    productAdapter.setData(list1);
                    Message_Notifycation=sp.toString();
                    sendNotifycation(chuyen_ArrByte_sang_Bitmap(ImagePresent));
                    dialog.dismiss();
                }

            }
        });

        dialog.show();

    }

    private List<SanPham> getListSP( List<SanPham> list){


//        list.add(new SanPham("Sach 1",1000,1,"anh",1000));
//        list.add(new SanPham("But bi",1000,1,"anh",1000));
//        list.add(new SanPham("but chi",1000,1,"anh",1000));
//        list.add(new SanPham("Sach 2",1000,1,"anh",1000));
//        list.add(new SanPham("Sach 3",1000,1,"anh",1000));
        return list;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data!= null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            avatar.setImageBitmap(bitmap);
            ImagePresent = chuyen_Bitmap_sang_ArrByte(bitmap);
        }
        else if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            //Lấy URI hình kết quả trả về
            selectedUriImage = data.getData();
//            System.out.println(selectedUriImage);
            //lấy đường dẫn hình
            picturePath=getPicturePath(selectedUriImage);
//            System.out.println(picturePath);
            InputStream inputStream = null;
            try {
                inputStream = getContext().getContentResolver().openInputStream(selectedUriImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream);
            avatar.setImageBitmap(selectedBitmap);
            ImagePresent = chuyen_Bitmap_sang_ArrByte(selectedBitmap);
        }



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
    private void sendNotifycation(Bitmap notificationto) {
        Intent notifyIntent = new Intent(getContext(), Home.class);
        // Set the Activity to start in a new, empty task
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Create the PendingIntent
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                getContext(), getNotificationID(), notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );
       // Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.notificationto);
        Notification notification = new NotificationCompat.Builder(getContext(), MyApplication.CHANNEL_ID)
                .setContentTitle("Sản phẩm mới được bày bán")
                .setContentText(Message_Notifycation)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(Message_Notifycation))
                .setSmallIcon(R.drawable.notification)
                .setLargeIcon(notificationto)
                .setContentIntent(notifyPendingIntent)
                .build();
        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if(notification!=null){
            notificationManager.notify(getNotificationID(),notification);
        }
    }

    private int getNotificationID() {
        return (int) new Date().getTime();
    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
package com.example.doancuoiki.admin.fragment;



import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.Toast;

import com.example.doancuoiki.MainActivity;
import com.example.doancuoiki.R;
import com.example.doancuoiki.SanPham;
import com.example.doancuoiki.UploadImgFBase;
import com.example.doancuoiki.Users;
import com.example.doancuoiki.admin.adapter.ProductAdapter;
import com.example.doancuoiki.admin.adapter.UserAdapter;
import com.example.doancuoiki.api.ApiService;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {
    View v;
    Boolean an= true;
    UserAdapter userAdapter;
    RecyclerView recyclerView;
    ImageView avatar,btnRefresh,btnAn;
    List<Users> list1 = new ArrayList<>();
    Button btn_Them;
    private Uri selectedUriImage;
    String picturePath;
    byte[] ImagePresent;
    int REQUEST_CODE_CAMERA=1,REQUEST_CODE_FOLDER =2;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserFragment() {
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
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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



    }
    public void callAPI_Register(Users user) {
        //System.out.println(user.toString());
        //Call<Users> res = ApiService.apiService.register(user);
        ContentResolver cR = getContext().getContentResolver();
//        SanPham sp = new SanPham("Sach cua Bua", 100, "Loai 1", 100000, "");
        UploadImgFBase fb = new UploadImgFBase();
        fb.set_for_user(user, selectedUriImage, cR, getContext(), 1);
        fb.actionUpload();
    }

    private void CallAPI() {
        Call<List<Users>> listCall = ApiService.apiService.getAllUsers();
        listCall.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                List<Users> list3= new ArrayList<>();
                list3 = response.body();
                for(int i=0;i<list3.size();i++) {
                    list1.add(list3.get(i));

                }
                userAdapter.setData(list1);

            }
            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                Toast.makeText(getContext(), "Call API Error", Toast.LENGTH_SHORT).show();
                Log.d("false", "sai ");
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_user, container, false);
        recyclerView=(RecyclerView)v.findViewById(R.id.rcv_listUser);
        btnAn=(ImageView)v.findViewById(R.id.btn_an_user_frag);

        btnRefresh=(ImageView)v.findViewById(R.id.btn_refesh_user_frag);
        btnRefresh.setVisibility(View.INVISIBLE);
        btn_Them=(Button)v.findViewById(R.id.btn_them_user_frag) ;
        btn_Them.setVisibility(View.INVISIBLE);

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list1.clear();
                CallAPI();
                recyclerView.setAdapter(userAdapter);
            }
        });
        btnAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(an){
                    btnRefresh.setVisibility(View.VISIBLE);
                    btn_Them.setVisibility(View.VISIBLE);
                    an=false;
                }
                else {
                    btnRefresh.setVisibility(View.INVISIBLE);
                    btn_Them.setVisibility(View.INVISIBLE);
                    an=true;
                }
            }
        });
        btn_Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(Gravity.CENTER);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);


        userAdapter= new UserAdapter(getActivity(),list1);
        CallAPI();
        //Log.d("size l1", "onCreateView: "+list1.size());
        recyclerView.setAdapter(userAdapter);

        return v;
    }
    //    private void openDialogSua(int gratity, Users user){
//        final Dialog dialog =  new Dialog(getContext());
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_sua_user);
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
//        EditText edtTen=(EditText) dialog.findViewById(R.id.edt_ten_user_diaglogsua);
//        EditText edtusername=(EditText) dialog.findViewById(R.id.edt_username_dialogsua);
//        EditText edtpassword=(EditText) dialog.findViewById(R.id.edt_password_dialogsua);
//        EditText edtemail=(EditText) dialog.findViewById(R.id.edt_mail_dialogsua);
//        EditText edtsdt=(EditText) dialog.findViewById(R.id.edt_sdt_dialogsua);
//        EditText edtdiachi=(EditText) dialog.findViewById(R.id.edt_diachi_dialogsua);
//        RadioButton rbnam=(RadioButton)dialog.findViewById(R.id.rb_nam_dialogsua);
//        RadioButton rbnu=(RadioButton)dialog.findViewById(R.id.rb_nu_dialogsua);
//        RadioButton rbadmin=(RadioButton)dialog.findViewById(R.id.rb_admin_dialogsua);
//        RadioButton rbuser=(RadioButton)dialog.findViewById(R.id.rb_user_dialogsua);
//
//        Button btn_xacnhan=(Button)dialog.findViewById(R.id.btn_xacnhan_user_dialogsua);
//        Button btn_huy=(Button)dialog.findViewById(R.id.btn_huy_user_dialogsua);
//        Button btn_Folder = (Button)dialog.findViewById(R.id.btn_folder_user_dialogsua);
//        Button btn_Camera = (Button)dialog.findViewById(R.id.btn_camera_user_dialogsua);
//        avatar = (ImageView)dialog.findViewById(R.id.img_dia_avatar_user_dialogsua);
//
//
//        edtTen.setText(user.getName());
//        edtusername.setText(user.getUsername());
//        edtpassword.setText(user.getPassword());
//        edtdiachi.setText(user.getDiachi());
//        edtemail.setText(user.getMail());
//        edtsdt.setText(user.getSdt());
//        if(user.isGioitinh())
//            rbnam.setChecked(true);
//        else rbnu.setChecked(true);
//        if (user.getRole()=="admin")
//            rbadmin.setChecked(true);
//        else rbuser.setChecked(true);
//
//        avatar.setImageBitmap(chuyen_ArrByte_sang_Bitmap(user.getImg()));
//        ImagePresent = user.getImg();
//        btn_Camera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent,REQUEST_CODE_CAMERA);
//            }
//        });
//        btn_Folder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(intent,REQUEST_CODE_FOLDER);
//            }
//        });
//
//        btn_huy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String ten= edtTen.getText().toString().trim();
//                String username=edtusername.getText().toString().trim();
//                String password=edtpassword.getText().toString().trim();
//                String email=edtemail.getText().toString().trim();
//                String sdt=edtsdt.getText().toString().trim();
//                String diachi=edtdiachi.getText().toString().trim();
//                boolean phai;
//                String role;
//                if(rbnam.isChecked())
//                    phai=true;
//                else
//                    phai=false;
//                if(rbadmin.isChecked())
//                    role="admin";
//                else
//                    role="user";
//
//                byte[] resize = chuyen_Bitmap_sang_ArrByte(getResizedBitmap(chuyen_ArrByte_sang_Bitmap(ImagePresent), 100, 100));
//
//                Users users = new Users(ten,username,password,phai,email,diachi,sdt,role,resize);
//                callAPI_update(users);
//                user.setName(ten);
//                user.setDiachi(diachi);
//                user.setMail(email);
//                user.setUsername(username);
//                user.setPassword(password);
//                user.setRole(role);
//                user.setGioitinh(phai);
//                user.setSdt(sdt);
//
//                Toast.makeText(getContext(), "đã update", Toast.LENGTH_SHORT).show();
//                userAdapter.setData(list1);
//                //productAdapter.setData(list1);
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//    }
    public void callAPI_Delete(Users user) {
        System.out.println(user.toString());
        Call<Users> res = ApiService.apiService.delete(user);
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
    private void openDialog(int gratity) {
        final Dialog dialog =  new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_user);

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
        EditText edtTen=(EditText) dialog.findViewById(R.id.edt_ten_user_diaglogthem);
        EditText edtusername=(EditText) dialog.findViewById(R.id.edt_username_dialogthem);
        EditText edtpassword=(EditText) dialog.findViewById(R.id.edt_password_dialogthem);
        EditText edtemail=(EditText) dialog.findViewById(R.id.edt_mail_dialogthem);
        EditText edtsdt=(EditText) dialog.findViewById(R.id.edt_sdt_dialogthem);
        EditText edtdiachi=(EditText) dialog.findViewById(R.id.edt_diachi_dialogthem);
        RadioButton rbnam=(RadioButton)dialog.findViewById(R.id.rb_nam_dialogthem);
        RadioButton rbnu=(RadioButton)dialog.findViewById(R.id.rb_nu_dialogthem);
        RadioButton rbadmin=(RadioButton)dialog.findViewById(R.id.rb_admin_dialogthem);
        RadioButton rbuser=(RadioButton)dialog.findViewById(R.id.rb_user_dialogthem);
        Button btnFolder = (Button)dialog.findViewById(R.id.btn_folder_user_diaglogthem);
        Button btnCamera = (Button)dialog.findViewById(R.id.btn_camera_user_dialogthem);
        avatar = (ImageView) dialog.findViewById(R.id.img_dia_avatar_user_dialogthem);


        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_CODE_CAMERA);
            }
        });
        btnFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_FOLDER);
            }
        });


        Button btn_xacnhan1=(Button)dialog.findViewById(R.id.btn_xacnhan_user_dialogthem);
        Button btn_huy1=(Button)dialog.findViewById(R.id.btn_huy_user_diaglogthem);
        btn_huy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_xacnhan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtTen.getText().toString().isEmpty()
                    ||  edtusername.getText().toString().isEmpty()
                        || edtemail.getText().toString().isEmpty()
                            || edtsdt.getText().toString().isEmpty()
                                ||  edtdiachi.getText().toString().isEmpty()

                )   {
                    Toast.makeText(getContext(), "Hãy nhập đủ thông tin", Toast.LENGTH_SHORT).show();

                }
                else {
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

                    Users users = new Users(ten,username,password,phai,mail,diachi,sdt,role,"");

                    callAPI_Register(users);
                /*list1.clear();
                CallAPI();
                recyclerView.setAdapter(userAdapter);*/
                    list1.add(users);
                    userAdapter.setData(list1);

                    dialog.dismiss();
                }

            }
        });

        dialog.show();
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

}
package com.example.doancuoiki.api;

import com.example.doancuoiki.CTDDHinHistory;
import com.example.doancuoiki.Class.Product;
import com.example.doancuoiki.DDH;
import com.example.doancuoiki.ObjectTK;
import com.example.doancuoiki.SanPham;
import com.example.doancuoiki.TT_CTDDH_SP;
import com.example.doancuoiki.ThangThongKe;
import com.example.doancuoiki.UserLogged;
import com.example.doancuoiki.Users;
import com.example.doancuoiki.ObjectLogin;
import com.example.doancuoiki.objectDDH.CTDDH;
import com.example.doancuoiki.objectDDH.DonDatHang;
import com.example.doancuoiki.objectDDH.resDDH;
import com.example.doancuoiki.objectDDH.truyenDDH;
import com.example.doancuoiki.objectOTP;
import com.example.doancuoiki.objectQuenMK.object_confirm_pass;
import com.example.doancuoiki.objectQuenMK.resXacNhanQuenMK;
import com.example.doancuoiki.objectQuenMK.token_forgotpass;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface ApiService {
    //https://api-cuoiky.herokuapp.com/api/select/user?fbclid=IwAR0n5neTeuF99x5Gpy0Qd2PZgW2iwmj3hz_0XtIthlyVr4So0E4nOmiUwXQ
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://api-cuoiky.herokuapp.com/")
            .client(new OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("api/select/user")
    Call<List<Users>> getAllUsers();
//    @Query("GioiTinh")  Boolean GioiTinh,
//    @Query("name") String name,
//    @Query("username") String username,
//    @Query("password") String password,
//    @Query("email") String email,
//    @Query("SDT") String SDT,
//    @Query("DiaChi") String DiaChi,
//    @Query("Role") String Role,
//    @Query("img") String img,
//    @Query("date") String date

    @POST("api/user/login")
    Call<UserLogged> login(@Body ObjectLogin input);

    @GET("api/post")
    Call<Users> post(@Header("auth-token") String token);

    @POST("api/user/register")
    Call<Users> register(@Body Users user);

    @HTTP(method = "DELETE", path = "api/user/del", hasBody = true)
    Call<Users> delete(@Body Users user);

    @HTTP(method = "PUT", path = "api/user/edit", hasBody = true)
    Call<Users> putEdit(@Body Users user);

    @POST("api/support/forgotpass")
    Call<token_forgotpass> forgotPass(@Body resXacNhanQuenMK res);

    @POST("api/support/confirmchangepass")
    Call<resXacNhanQuenMK> confirmForgotPass(@Body object_confirm_pass input);

    @GET("api/select/product")
    Call<List<Product>> getAllProducts();

    @POST("api/ddh/insert")
    Call<resDDH> insertDDH(@Body truyenDDH input);

    @POST("api/ctddh/insert")
    Call<CTDDH> insertCTDDH(@Body CTDDH input);

    @POST("api/select/findCTDDHUser")
    Call<CTDDHinHistory> selectCTDDHHistory(@Body UserLogged user);

    @POST("api/select/findDDHUser")
    Call<List<DonDatHang>> selectDDHHistory(@Body UserLogged user);

    @POST("api/select/findCTDDHDdh")
    Call<CTDDHinHistory> selectCTDDHinDDH(@Body DonDatHang ddh);

    //======PRODUCT==================================
    @GET("api/select/product")
    Call<List<SanPham>> getAllPruduct();

    //https://api-cuoiky.herokuapp.com/api/product/insert
    @POST("api/product/insert")
    Call<SanPham> insertProduct(@Body SanPham sanPham);

    @HTTP(method = "DELETE", path = "api/product/delete", hasBody = true)
    Call<SanPham> deleteSP(@Body SanPham sanPham);

    @HTTP(method = "PUT", path = "api/product/update", hasBody = true)
    Call<SanPham> putEditPro(@Body SanPham sanPham);
    //======DDH==================================
    @GET("api/select/ddh")
    Call<List<DDH>> getAllDDH();
    @HTTP(method = "POST", path = "api/select/findCTDDHDdh", hasBody = true)
    Call<TT_CTDDH_SP> getCTDDH(@Body  DDH ddh);

    @HTTP(method = "PATCH", path = "api/support/acpAdmin", hasBody = true)
    Call<DDH> xacnhan(@Body  DDH ddh);

    //=====Thong Ke=====================================
    @HTTP(method = "POST", path = "api/support/top5month", hasBody = true)
    Call<List<ObjectTK>> top5(@Body ThangThongKe thang);


    @POST("api/support/otpDDH")
    Call<objectOTP> otpDDH(@Body UserLogged input);

    @PATCH("api/support/acpOTP")
    Call<DonDatHang> acpOTP(@Body DonDatHang input);


}

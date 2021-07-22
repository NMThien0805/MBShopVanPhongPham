package com.example.doancuoiki.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.doancuoiki.R;
import com.example.doancuoiki.SanPham;
import com.example.doancuoiki.UserLogged;
import com.example.doancuoiki.Users;
import com.example.doancuoiki.admin.fragment.AdminFragment;
import com.example.doancuoiki.admin.fragment.ProductFragment;
import com.example.doancuoiki.admin.fragment.ThongKeFragment;
import com.example.doancuoiki.admin.fragment.UserFragment;
import com.example.doancuoiki.admin.fragment.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainAdminActivity extends AppCompatActivity {
    //private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private ViewPagerAdapter viewPagerAdapter;
    Context context;
    FrameLayout frameLayout;
    UserLogged usersdn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);


        //viewPager = findViewById(R.id.view_pager);
        bottomNavigationView=findViewById(R.id.bottom_nevigation);
        frameLayout = findViewById(R.id.framelayoutid);
        Bundle bundleReceive =getIntent().getExtras();
        if(bundleReceive !=null){
            usersdn = (UserLogged) bundleReceive.get("object_user");
            if(usersdn!=null){
                Toast.makeText(getApplicationContext(), "đăng nhập thành công", Toast.LENGTH_SHORT).show();
            }
        }
        Bundle bundleUserSend =new Bundle();
        bundleUserSend.putSerializable("object_user",usersdn);
        setFragment(new AdminFragment(),bundleUserSend);


        /*ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);*/
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_ad:
                        //viewPager.setCurrentItem(0);
                        bottomNavigationView.setItemBackgroundResource(R.color.white);
                        setFragment(new AdminFragment());
                        return true;
                    case R.id.menu_dh:
                        bottomNavigationView.setItemBackgroundResource(R.color.white);
                        setFragment(new ProductFragment());
                        //viewPager.setCurrentItem(1);
                        return true;
                    case R.id.menu_users:
                        bottomNavigationView.setItemBackgroundResource(R.color.white);
                        setFragment(new UserFragment());
                        //viewPager.setCurrentItem(2);
                        return true;
                    case R.id.menu_thongke:
                        bottomNavigationView.setItemBackgroundResource(R.color.white);
                        //viewPager.setCurrentItem(3);
                        setFragment(new ThongKeFragment());
                        return true;
                    default:
                        return false;
                }

            }
        });
        /*viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.menu_ad).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.menu_dh).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.menu_users).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.menu_thongke).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayoutid,fragment);
        fragmentTransaction.commit();
    }
    private void setFragment(Fragment fragment,Bundle bundle) {

        FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayoutid,fragment);
        fragment.setArguments(bundle);
        fragmentTransaction.commit();

    }


}
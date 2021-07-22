package com.example.doancuoiki.admin.thongke;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ThongKeViewPagerAdapter extends FragmentStatePagerAdapter {
    public ThongKeViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new DDHFragment();
            case 1:
                return new TKSPFragment();
            /*case 2:
                return new Tab3Fragment();*/
            default:
                return new DDHFragment();

        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
       switch (position) {
           case 0:
               return "Đơn đặt hàng";
           case 1:
               return "Biểu đồ";
           /*case 2:
               return "Tab 3";*/
           default:
               return "Đơn đặt hàng";

       }
    }
}

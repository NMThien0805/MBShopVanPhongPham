package com.example.doancuoiki;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.example.doancuoiki.Class.Product;
import com.example.doancuoiki.Fragment.CartFragment;
import com.example.doancuoiki.Fragment.DetailProductFragment;
import com.example.doancuoiki.Fragment.HistoryFragment;
import com.example.doancuoiki.Fragment.OrderInfoFragment;
import com.example.doancuoiki.Fragment.ProductFragment;
import com.example.doancuoiki.Fragment.inFoFragment;
import com.example.doancuoiki.objectDDH.DonDatHang;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    // region Variable

    private List<Product> listCartProduct;

    // Đếm số sản phẩm trong giỏ hàng
    private int countProduct;

    private AHBottomNavigation ahBotNavHome;
    private FragmentTransaction fragmentTransaction;

    UserLogged usersdn;

    // endregion Variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Bundle bundleReceive =getIntent().getExtras();
        if(bundleReceive !=null){
            usersdn = (UserLogged) bundleReceive.get("object_user");
            if(usersdn!=null){
                Toast.makeText(getApplicationContext(), "đăng nhập thành công", Toast.LENGTH_SHORT).show();
            }
        }
        Bundle bundleUserSend =new Bundle();
        bundleUserSend.putSerializable("object_user",usersdn);

        // Khởi tạo các item
        initItem();

        // Set data cho ahBotNavHome
        setDataBotNavHome(bundleUserSend);
    }

    // region  Private Menthod

    // Khởi tạo các item
    private void initItem() {
        ahBotNavHome = findViewById(R.id.ahbotnav_home);
        if(listCartProduct == null){
            listCartProduct = new ArrayList<>();
        }
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contet_frame, new ProductFragment());

        fragmentTransaction.commit();
    }

    // Set data cho BotNavHome
    private void setDataBotNavHome(Bundle bundle) {

        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_product, R.drawable.ic_baseline_home_24, R.color.teal_200);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_cart, R.drawable.ic_baseline_shopping_basket_24, R.color.teal_700);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_history, R.drawable.ic_baseline_history_24, R.color.purple_200);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("Info", R.drawable.ic_baseline_account_box_24, R.color.yellow);

        // Add items
        ahBotNavHome.addItem(item1);
        ahBotNavHome.addItem(item2);
        ahBotNavHome.addItem(item3);
        ahBotNavHome.addItem(item4);

        ahBotNavHome.setColored(true);

        // Set màu nav
        ahBotNavHome.setDefaultBackgroundColor(getResources().getColor(R.color.white));

        // Khi click vào các icon trên nav
        ahBotNavHome.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                switch (position){
                    case 0:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.contet_frame, new ProductFragment());
                        fragmentTransaction.commit();
                        break;

                    case 1:
                        Fragment frament = new CartFragment(listCartProduct);
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.contet_frame, frament);
                        frament.setArguments(bundle);
                        fragmentTransaction.commit();
                        break;
                    case 2:
                        Fragment frament1 = new HistoryFragment();
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.contet_frame, frament1);
                        frament1.setArguments(bundle);
                        fragmentTransaction.commit();
                        break;
                    case 3:
                        Fragment frament2 = new inFoFragment();
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.contet_frame, frament2);
                        frament2.setArguments(bundle);
                        fragmentTransaction.commit();
                        break;

                }

                return true;
            }
        });
    }

    // endregion Private Menthod

    // region Public Menthod

    // Set số lượng các sản phẩm trong giỏ hàng
    public void setCountProductInCart(int count){
        countProduct = count;
        AHNotification notification = new AHNotification.Builder()
                .setText(String.valueOf(count))
                .setBackgroundColor(ContextCompat.getColor(Home.this, R.color.red))
                .setTextColor(ContextCompat.getColor(Home.this, R.color.white))
                .build();
        ahBotNavHome.setNotification(notification, 1);
    }

    // Mở Fragment DetailProduct
    public void toDetailProductFragment(Product product){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contet_frame, new DetailProductFragment(product,listCartProduct));
        fragmentTransaction.commit();
    }

    // Mở Fragment OrderInfo
    public void toOrderInfoFragment(DonDatHang orderInfo){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contet_frame, new OrderInfoFragment(orderInfo));
        fragmentTransaction.addToBackStack(OrderInfoFragment.TAG);
        fragmentTransaction.commit();
    }

    // Thêm sản phẩm đã chọn vào giỏ hàng
    public void addToListCartProdct(Product product){
        listCartProduct.add(product);
    }

    // Lấy ra các sản phẩm đã thêm vào giỏ hàng
    public List<Product> getListCartProduct() {
        return listCartProduct;
    }

    // Lấy ra số lượng các sản phẩm đã thêm vào giỏ hàng
    public int getCountProduct() {
        return countProduct;
    }

    // Set lại số lượng của sản phẩm khi mua nhiều
    public void setCountForProduct(int possion, int countProduct){
        listCartProduct.get(possion).setNumProduct(countProduct);
    }

    // endregion Public Menthod
}

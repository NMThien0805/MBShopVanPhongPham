package com.example.doancuoiki.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.doancuoiki.Class.Product;
import com.example.doancuoiki.Home;
import com.example.doancuoiki.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DetailProductFragment extends Fragment {

    // region Variable

    private DecimalFormat format = new DecimalFormat("###,###,###");

    // Kiểm tra Product đã được thêm vào cart chưa
    private Boolean isAddToCart;

    // Activity
    private Home home;

    // View
    private View mView;
    private Product detailProduct;
    private List<Product> listCartProduct;
    private TextView tvDetailProductName,tvDetailProductPrice,tvDetailProductDescription;
    private Button btnDetailProductBuy;
    private ImageView imgDetailProductPhoto;

    // endregion Variable

    public DetailProductFragment(Product product,List<Product> listProduct) {
        detailProduct = product;
        listCartProduct = listProduct;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_detail_product, container, false);

        initItem();

        setValueItem();

        return mView;
    }

    // region Private menthod

    // Khởi tạo các item
    private void initItem(){

        isAddToCart = false;
        home = (Home) getActivity();
        tvDetailProductName = mView.findViewById(R.id.tv_detail_product_name);
        tvDetailProductPrice = mView.findViewById(R.id.tv_detail_product_price);
        imgDetailProductPhoto = mView.findViewById(R.id.img_detail_product_photo);
        btnDetailProductBuy = mView.findViewById(R.id.btn_detail_product_buy);

        if(listCartProduct == null){
            listCartProduct = new ArrayList<>();
        }
    }

    // set giá trị cho các item
    private void setValueItem(){
        if (detailProduct != null){
            tvDetailProductName.setText(detailProduct.getTensp());
            tvDetailProductPrice.setText(format.format(detailProduct.getGia() ) + " VNĐ");
//            imgDetailProductPhoto.setImageBitmap(chuyen_ArrByte_sang_Bitmap(detailProduct.getImg()));
            Picasso.get().load(detailProduct.getImg()).into(imgDetailProductPhoto);
//            tvDetailProductDescription.setText(detailProduct.getDescription());

            // Kiểm tra sản phẩm đã dc add vào giỏ hay chưa
            for (int i = 0;i< listCartProduct.size();i++){

                // Nếu sản  phẩm đã dc add
                if (listCartProduct.get(i).getTensp().equals(detailProduct.getTensp())){
                    isAddToCart = true;
                    btnDetailProductBuy.setText("Đã Thêm");
                    btnDetailProductBuy.setBackgroundResource(R.drawable.custom_button_gray);
                    break;
                }
            }

            btnDetailProductBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isAddToCart){
                        Toast.makeText(getActivity(),"Sản Phẩm này đã tồn tại trong giỏ hàng",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        isAddToCart = true;
                        btnDetailProductBuy.setText("Đã Thêm");
                        btnDetailProductBuy.setBackgroundResource(R.drawable.custom_button_gray);
                        detailProduct.setNumProduct(1);
                        home.addToListCartProdct(detailProduct);
                        home.setCountProductInCart(home.getCountProduct() + 1);
                        Toast.makeText(getActivity(),"Đã thêm sản phẩm vào giỏ hàng",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public Bitmap chuyen_ArrByte_sang_Bitmap(byte[] img){
//        System.out.println("show" + img);
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        return  bitmap;
    }

    // endregion Private menthod

}

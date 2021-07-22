package com.example.doancuoiki.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doancuoiki.Class.Product;
import com.example.doancuoiki.Home;
import com.example.doancuoiki.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private DecimalFormat formatPrice = new DecimalFormat("###,###,###");
    private List<Product> mListProduct;
    private Home home;

    // Khai báo Interface giúp cho việc click vào phần tử của recycleview
    public interface IClickItemProductListener{
        void onClickItemProduct(Product product);
    }

    public void setData(List<Product> mList, Home home) {
        this.mListProduct = mList;
        this.home = home;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        Product product = mListProduct.get(position);

        if (product == null) {
            return;
        }
        else {

            Picasso.get().load(product.getImg()).into(holder.imgPhotoProduct);
            holder.tvProductName.setText(product.getTensp());
            holder.tvProductPrice.setText(formatPrice.format(product.getGia()) + " VNĐ");

            holder.setItemClickListener(new IClickItemProductListener() {
                @Override
                public void onClickItemProduct(Product product) {
                    home.toDetailProductFragment(product);
                }
            });
        }
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

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgPhotoProduct;
        TextView tvProductName,tvProductPrice;
        IClickItemProductListener iClickItemProductListener;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPhotoProduct = itemView.findViewById(R.id.img_photo_product);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvProductPrice = itemView.findViewById(R.id.tv_product_price);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(IClickItemProductListener itemClickListener){
            this.iClickItemProductListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            this.iClickItemProductListener.onClickItemProduct(mListProduct.get(getAdapterPosition()));
        }
    }

    @Override
    public int getItemCount() {
        if (mListProduct != null) {
            return mListProduct.size();
        }
        else
            return 0;
    }
}

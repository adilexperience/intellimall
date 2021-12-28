package com.example.fypproject.intelimall.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.fypproject.intelimall.R;
import com.example.fypproject.intelimall.models.AddItemInCartModal;
import com.example.fypproject.intelimall.models.CartItemModal;
import com.example.fypproject.intelimall.models.CategoryButton;
import com.example.fypproject.intelimall.models.ProductModal;
import com.example.fypproject.intelimall.models.UpdateCartItemModal;
import com.example.fypproject.intelimall.network.ApiRequests;
import com.example.fypproject.intelimall.network.Persistent;
import com.example.fypproject.intelimall.utils.Constant;
import com.example.fypproject.intelimall.views.dashboard.ui.home.HomeFragment;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllProductsAdapter extends BaseAdapter{

    //views
    private Context mContext;
    private List<ProductModal> mProductsModalList;

    public AllProductsAdapter(Context mContext, List<ProductModal> mProductsModalList) {
        this.mContext = mContext;
        this.mProductsModalList = mProductsModalList;
    }

    @Override
    public int getCount() {
        return mProductsModalList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.newlayout, viewGroup, false);
        } else
            grid = (View) view;

        ProductModal productModal = mProductsModalList.get(i);



        TextView mTVProductTitle, mTVProductPrice, mTVProductCategory, mTVProductDescription;
        ImageView ivProductImage;
        RelativeLayout rlProduct;

        mTVProductPrice = grid.findViewById(R.id.product_price);
        mTVProductTitle = grid.findViewById(R.id.product_name);
        mTVProductDescription = grid.findViewById(R.id.product_description);
        ivProductImage = grid.findViewById(R.id.product_image);
        mTVProductCategory = grid.findViewById(R.id.tv_product_category);
        rlProduct = grid.findViewById(R.id.rl_product);

        rlProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.openProductDetailsUI(mContext, productModal);
            }
        });

        mTVProductTitle.setText(productModal.getTitle());
        mTVProductPrice.setText("RS:" + productModal.getPrice());
        mTVProductDescription.setText(productModal.getDescription());
        mTVProductCategory.setText(productModal.getCategory().toUpperCase());
        if(productModal.getImage_url().startsWith("data")) {
            // convert data base64 to image
            String base64String = productModal.getImage_url();
            String base64Image = base64String.split(",")[1];
            byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ivProductImage.setImageBitmap(decodedByte);
        }else
            Picasso.get().load(productModal.getImage_url()).into(ivProductImage);

        return grid;
    }
}

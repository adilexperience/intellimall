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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fypproject.intelimall.R;
import com.example.fypproject.intelimall.models.CartItemModal;
import com.example.fypproject.intelimall.models.CategoryButton;
import com.example.fypproject.intelimall.models.ProductModal;
import com.example.fypproject.intelimall.network.ApiRequests;
import com.example.fypproject.intelimall.utils.Constant;
import com.example.fypproject.intelimall.views.dashboard.ui.home.HomeFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.MyViewHolder> {

    //views
    private Context mContext;
    private List<CartItemModal> mCartItemsModalList;
    private View view;

    public ShoppingCartAdapter(Context mContext, List<CartItemModal> mCartItemsModalList) {
        this.mContext = mContext;
        this.mCartItemsModalList = mCartItemsModalList;
    }

    @NonNull
    @Override
    public ShoppingCartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.cart_item, parent, false);
        return new ShoppingCartAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCartAdapter.MyViewHolder holder, int position) {
        final CartItemModal cartItem = mCartItemsModalList.get(position);

        holder.tvProductTitle.setText(cartItem.getProduct().getTitle());
        holder.tvProductQuantity.setText(cartItem.getQuantity() + "");

        if(cartItem.getProduct().getImage_url().startsWith("data")) {
            // convert data base64 to image
            String base64String = cartItem.getProduct().getImage_url();
            String base64Image = base64String.split(",")[1];
            byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.ivProduct.setImageBitmap(decodedByte);
        }else
            Picasso.get().load(cartItem.getProduct().getImage_url()).into(holder.ivProduct);

        holder.llNetPrice.setVisibility(View.VISIBLE);
        holder.llProductPrice.setVisibility(View.VISIBLE);

        holder.tvProductPrice.setText(cartItem.getProduct().getPrice());
        holder.tvNetPrice.setText((cartItem.getQuantity() * Double.parseDouble(cartItem.getProduct().getPrice())) + " PKR");
    }

    @Override
    public int getItemCount() {
        return mCartItemsModalList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProduct;
        TextView tvProductTitle, tvProductQuantity, tvProductPrice, tvNetPrice;
        LinearLayout llProductPrice, llNetPrice;

        MyViewHolder(View itemView) {
            super(itemView);
            llProductPrice = itemView.findViewById(R.id.ll_product_price);
            llNetPrice = itemView.findViewById(R.id.ll_product_net_price);

            ivProduct = itemView.findViewById(R.id.iv_product);
            tvProductTitle = itemView.findViewById(R.id.product_title);
            tvProductQuantity = itemView.findViewById(R.id.product_quantity);
            tvProductPrice = itemView.findViewById(R.id.tv_product_price);
            tvNetPrice = itemView.findViewById(R.id.tv_net_price);
        }

    }
}

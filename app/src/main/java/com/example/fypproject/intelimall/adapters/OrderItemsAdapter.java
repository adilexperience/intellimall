package com.example.fypproject.intelimall.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fypproject.intelimall.R;
import com.example.fypproject.intelimall.models.CartItemModal;
import com.example.fypproject.intelimall.models.FeedbackModal;
import com.example.fypproject.intelimall.models.OrderItemModal;
import com.example.fypproject.intelimall.models.ProductModal;
import com.example.fypproject.intelimall.network.ApiRequests;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.MyViewHolder> {

    //views
    private Context mContext;
    private List<OrderItemModal> mOrderItemsModalList;
    private View view;

    public OrderItemsAdapter(Context mContext, List<OrderItemModal> mOrderItemsModalList) {
        this.mContext = mContext;
        this.mOrderItemsModalList = mOrderItemsModalList;
    }

    @NonNull
    @Override
    public OrderItemsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.cart_item, parent, false);
        return new OrderItemsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemsAdapter.MyViewHolder holder, int position) {
        final OrderItemModal orderItem = mOrderItemsModalList.get(position);

        holder.tvProductTitle.setText(orderItem.getProducts().getTitle());
        holder.tvProductQuantity.setText(orderItem.getQuantity() + "");

        if(orderItem.getProducts().getImage_url().startsWith("data")) {
            // convert data base64 to image
            String base64String = orderItem.getProducts().getImage_url();
            String base64Image = base64String.split(",")[1];
            byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.ivProduct.setImageBitmap(decodedByte);
        }else
            Picasso.get().load(orderItem.getProducts().getImage_url()).into(holder.ivProduct);

        holder.llNetPrice.setVisibility(View.VISIBLE);
        holder.llProductPrice.setVisibility(View.VISIBLE);

        holder.tvProductPrice.setText(orderItem.getPrice());
        holder.tvNetPrice.setText((orderItem.getQuantity() * Double.parseDouble(orderItem.getPrice())) + " PKR");
    }

    @Override
    public int getItemCount() {
        return mOrderItemsModalList.size();
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

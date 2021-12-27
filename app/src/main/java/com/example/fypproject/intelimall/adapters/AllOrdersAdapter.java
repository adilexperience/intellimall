package com.example.fypproject.intelimall.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fypproject.intelimall.R;
import com.example.fypproject.intelimall.models.CategoryButton;
import com.example.fypproject.intelimall.models.OrderModal;
import com.example.fypproject.intelimall.utils.Constant;
import com.example.fypproject.intelimall.views.dashboard.ui.home.HomeFragment;

import java.util.List;

public class AllOrdersAdapter extends RecyclerView.Adapter<AllOrdersAdapter.MyViewHolder> {

    //views
    private Context mContext;
    private List<OrderModal> mAllOrdersModalList;
    private View view;

    public AllOrdersAdapter(Context mContext, List<OrderModal> mAllOrdersModalList) {
        this.mContext = mContext;
        this.mAllOrdersModalList = mAllOrdersModalList;
    }

    @NonNull
    @Override
    public AllOrdersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.order_item, parent, false);
        return new AllOrdersAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllOrdersAdapter.MyViewHolder holder, int position) {
        final OrderModal order = mAllOrdersModalList.get(position);

        holder.tvOrderID.setText(order.getId() + "");
        holder.tvOrderPrice.setText(order.getPrice());
        holder.tvOrderStatus.setText(order.getStatus());

        holder.cvOrderItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.openOrderDetailUI(mContext, order);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAllOrdersModalList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cvOrderItem;
        TextView tvOrderID, tvOrderPrice, tvOrderStatus;

        MyViewHolder(View itemView) {
            super(itemView);
            cvOrderItem = itemView.findViewById(R.id.cv_order_item);
            tvOrderID = itemView.findViewById(R.id.tv_order_id);
            tvOrderPrice = itemView.findViewById(R.id.tv_order_price);
            tvOrderStatus = itemView.findViewById(R.id.tv_order_status);
        }

    }
}

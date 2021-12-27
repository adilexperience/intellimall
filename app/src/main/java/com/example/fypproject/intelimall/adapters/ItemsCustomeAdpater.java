package com.example.fypproject.intelimall.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fypproject.intelimall.utils.Constant;
import com.example.fypproject.intelimall.R;

public class ItemsCustomeAdpater extends RecyclerView.Adapter<ItemsCustomeAdpater.ViewHolder> {
    private Context mContext;

    public ItemsCustomeAdpater(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(mContext);
        View view=layoutInflater.inflate(R.layout.design_cart,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.item.setText(Constant.globalArraylist.get(position).getTitle());
        holder.price.setText("Rs. "+Constant.globalArraylist.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return Constant.globalArraylist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
       TextView item,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item= itemView.findViewById(R.id.textview_item);
            price=itemView.findViewById(R.id.textview_price);
        }
    }
}

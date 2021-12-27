package com.example.fypproject.intelimall.adapters;

import static com.example.fypproject.intelimall.utils.Constant.globalArraylist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fypproject.intelimall.R;
import com.example.fypproject.intelimall.listner.SimpleClickListener;
import com.example.fypproject.intelimall.models.ProductModal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements Filterable {

    Context context;
    List<ProductModal> productsList;
    ArrayList<ProductModal> productsListAll;
    ArrayList<ProductModal> filterList;

    SimpleClickListener clickListener;

    List<Integer> list = new ArrayList<>();
    List<String> Names = new ArrayList<>();


    public ProductAdapter(Context context, List<ProductModal> productsList) {
        this.context = context;
        this.productsList = productsList;
        this.productsListAll = new ArrayList<>(productsList);
        this.filterList = new ArrayList<>();
    }

    public  void Clicked(SimpleClickListener listener)
    {
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.newlayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ProductModal products=productsList.get(position);
        holder.name.setText(products.getTitle());
        holder.price.setText("Rs: "+products.getPrice());
        int id=productsList.get(position).getId();
        Glide
                .with(context)
                .load(products.getImage_url())
                .placeholder(R.drawable.laoding)
                .into(holder.image);
        holder.submitBtn.setOnClickListener(view -> {
           // holder.submitBtn.setText("Added");
          //  Toast.makeText(context,""+id,Toast.LENGTH_SHORT).show();
           // Toast.makeText(view.getContext(),"Item added to Cart",Toast.LENGTH_SHORT).show();

            if (!globalArraylist.contains(products)){
                globalArraylist.add(products);
                Log.e("size_arra",globalArraylist.size()+"");
                Toast.makeText(context,"Successfuly Added to Cart",Toast.LENGTH_SHORT).show();
            }
        });
        //holder.image.setText(ld.getMovie());
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            filterList = new ArrayList<>();
            if (charSequence.toString().isEmpty()) {
                filterList.addAll(productsList);
            } else {
                for (ProductModal products : productsListAll) {
                    if (products.getTitle().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filterList.add(products);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            productsList.clear();
            productsList.addAll((Collection<? extends ProductModal>) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name,price;
        ImageView image;
        Button submitBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.product_name);
            price=(TextView)itemView.findViewById(R.id.product_price);
            image=itemView.findViewById(R.id.product_image);
            submitBtn = itemView.findViewById(R.id.submit_btn);
        }
    }
}

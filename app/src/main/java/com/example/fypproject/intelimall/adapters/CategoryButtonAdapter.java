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
import com.example.fypproject.intelimall.utils.Constant;
import com.example.fypproject.intelimall.views.dashboard.ui.home.HomeFragment;

import java.util.List;

public class CategoryButtonAdapter extends RecyclerView.Adapter<CategoryButtonAdapter.MyViewHolder> {

    //views
    private Context mContext;
    private List<CategoryButton> mCategoryButtonsModalList;
    private View view;

    public CategoryButtonAdapter(Context mContext, List<CategoryButton> mCategoryButtonsModalList) {
        this.mContext = mContext;
        this.mCategoryButtonsModalList = mCategoryButtonsModalList;
    }

    @NonNull
    @Override
    public CategoryButtonAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.category_button, parent, false);
        return new CategoryButtonAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryButtonAdapter.MyViewHolder holder, int position) {

        final CategoryButton chatButton = mCategoryButtonsModalList.get(position);

        holder.mTVCardTitle.setText(chatButton.getTitle());
        if(chatButton.isSelected()) {
            holder.mCV.setCardBackgroundColor(mContext.getColor(R.color.orange));
            holder.mTVCardTitle.setTextColor(mContext.getColor(R.color.white));
            holder.mTVCardTitle.setTypeface(null, Typeface.BOLD);
        }else {
            holder.mCV.setCardBackgroundColor(mContext.getColor(R.color.white));
            holder.mTVCardTitle.setTextColor(mContext.getColor(R.color.orange));
            holder.mTVCardTitle.setTypeface(null, Typeface.NORMAL);
        }

        // adding click listener for card and moving intent
        holder.mCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryButton _clickedCard = HomeFragment.categoryButtonsArrayList.get(Constant.getCategoryItemIndex(HomeFragment.categoryButtonsArrayList, chatButton.getId()));
                CategoryButton _selectedCard;

                for (CategoryButton button : HomeFragment.categoryButtonsList) {
                    if(button.isSelected()) {
                        _selectedCard = button;
                        if(_clickedCard.getId() != _selectedCard.getId()) {
                            HomeFragment.updateSelectedCard(_clickedCard.getId());
                            _clickedCard.setSelected(true);
                            _selectedCard.setSelected(false);

                            notifyItemChanged(Constant.getCategoryItemIndex(HomeFragment.categoryButtonsArrayList, _clickedCard.getId()));
                            notifyItemChanged(Constant.getCategoryItemIndex(HomeFragment.categoryButtonsArrayList, _selectedCard.getId()));
                        }
                    }
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mCategoryButtonsModalList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        CardView mCV;
        TextView mTVCardTitle;

        MyViewHolder(View itemView) {
            super(itemView);
            mCV = itemView.findViewById(R.id.cv_btn);
            mTVCardTitle = itemView.findViewById(R.id.tv_btn_title);
        }

    }
}

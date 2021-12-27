package com.example.fypproject.intelimall.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fypproject.intelimall.R;
import com.example.fypproject.intelimall.models.CategoryButton;
import com.example.fypproject.intelimall.models.FeedbackModal;
import com.example.fypproject.intelimall.models.FeedbackRequestModal;
import com.example.fypproject.intelimall.network.ApiRequests;
import com.example.fypproject.intelimall.utils.Constant;
import com.example.fypproject.intelimall.views.dashboard.ui.feedback.FeedbackFragment;
import com.example.fypproject.intelimall.views.dashboard.ui.home.HomeFragment;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingFeedbacksAdapter extends RecyclerView.Adapter<PendingFeedbacksAdapter.MyViewHolder> {

    //views
    private Context mContext;
    private List<FeedbackModal> mFeedbacksModalList;
    private View view;

    public PendingFeedbacksAdapter(Context mContext, List<FeedbackModal> mFeedbacksModalList) {
        this.mContext = mContext;
        this.mFeedbacksModalList = mFeedbacksModalList;
    }

    @NonNull
    @Override
    public PendingFeedbacksAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.feedback_item, parent, false);
        return new PendingFeedbacksAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingFeedbacksAdapter.MyViewHolder holder, int position) {
        final FeedbackModal feedback = mFeedbacksModalList.get(position);

        holder.tvOrderID.setText("You Order #" + feedback.getOrder_id() + " is completed and we like to get your feedback on service and product quality.");
        holder.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _feedback = holder.etFeedback.getText().toString().trim();
                float rating = holder.ratingBar.getRating();
                if(_feedback.isEmpty())
                    holder.etFeedback.setError("Enter feedback before proceeding");
                else {
                    // make api call to complete this feedback
                    Date currentTime = Calendar.getInstance().getTime();
                    FeedbackRequestModal feedbackRequest = new FeedbackRequestModal(feedback.getId(), currentTime.toString(), "completed", rating + "", _feedback);

                    Call<Void> completePendingFeedbackModalCall = ApiRequests.getApiService().updateFeedback(feedbackRequest);
                    completePendingFeedbackModalCall.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            mFeedbacksModalList.remove(holder.getAdapterPosition());
                            notifyItemRemoved(holder.getAdapterPosition());
                            notifyItemRangeChanged(holder.getAdapterPosition(), mFeedbacksModalList.size());
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFeedbacksModalList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvOrderID;
        EditText etFeedback;
        RatingBar ratingBar;
        Button btnSubmit;

        MyViewHolder(View itemView) {
            super(itemView);
            tvOrderID = itemView.findViewById(R.id.tv_order_id);
            etFeedback = itemView.findViewById(R.id.et_feedback);
            ratingBar = itemView.findViewById(R.id.rating_bar);
            btnSubmit = itemView.findViewById(R.id.btn_submit_feedback);
        }

    }
}

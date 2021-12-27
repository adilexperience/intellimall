package com.example.fypproject.intelimall.views.dashboard.ui.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fypproject.intelimall.R;
import com.example.fypproject.intelimall.adapters.AllProductsAdapter;
import com.example.fypproject.intelimall.adapters.CategoryButtonAdapter;
import com.example.fypproject.intelimall.adapters.OrderItemsAdapter;
import com.example.fypproject.intelimall.models.CartItemModal;
import com.example.fypproject.intelimall.models.CategoryButton;
import com.example.fypproject.intelimall.models.FeedbackModal;
import com.example.fypproject.intelimall.models.OrderItemModal;
import com.example.fypproject.intelimall.models.OrderModal;
import com.example.fypproject.intelimall.models.ProductModal;
import com.example.fypproject.intelimall.models.UserModal;
import com.example.fypproject.intelimall.network.ApiRequests;
import com.example.fypproject.intelimall.network.Persistent;
import com.example.fypproject.intelimall.utils.Constant;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends AppCompatActivity {

    private RecyclerView _rvOrderItems;
    private TextView _tvOrderID, _tvOrderStatus, _tvOrderPrice, _tvOrderItemsCount, _tvOrderReviews;
    private LinearLayout _llFeedback;
    private RatingBar _ratingBar;

    // variables
    public static OrderModal _order;
    private FeedbackModal _feedback;
    public UserModal _user;
    private OrderItemsAdapter _orderItemsAdapter;
    private boolean _isFeedBackGiven = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        // initialize views with ids
        _bindViewsWithID();

        // get user data from shared preferences
        _user = Persistent.getLoggedInUser(getApplicationContext());

        // getting data from intent and making horizontal buttons lists as per available data
        _getIntent();

        _checkIfFeedbackIsGivenOrNot();

        // mapping values to UI
        _mappingValuesOnUI();
    }

    void _checkIfFeedbackIsGivenOrNot() {
        Log.e("ORDER_DETAILS", _user.getId() + "");
        Log.e("ORDER_DETAILS", _order.getId() + "");
        Call<FeedbackModal> feedbackModalCall = ApiRequests.getApiService().orderFeedback(_user.getId()+"", _order.getId()+"");
        feedbackModalCall.enqueue(new Callback<FeedbackModal>() {
            @Override
            public void onResponse(Call<FeedbackModal> call, Response<FeedbackModal> response) {
                FeedbackModal feedback = response.body();
                if(feedback != null) {
                    _feedback = feedback;
                    _isFeedBackGiven = !(_feedback.getComment().trim().isEmpty());
                    if(_isFeedBackGiven) {
                        _llFeedback.setVisibility(View.VISIBLE);
                        _tvOrderReviews.setText(_feedback.getComment());
                        _ratingBar.setRating((float) _feedback.getRating());

                    }
                }
            }

            @Override
            public void onFailure(Call<FeedbackModal> call, Throwable t) {

            }
        });
    }

    void _mappingValuesOnUI() {
        _tvOrderID.setText(_order.getId() + "");
        _tvOrderStatus.setText(_order.getStatus());
        _tvOrderPrice.setText("RS:" + _order.getPrice());
        _tvOrderItemsCount.setText(_order.getItem().size() + "");

        // configuring recycler view
        _rvOrderItems.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false);
        _rvOrderItems.setLayoutManager(layoutManager);

        ArrayList<OrderItemModal> _orderItemsModalList = new ArrayList<>(_order.getItem());
//        // mapping   values to adapter and displaying to UI
        _orderItemsAdapter = new OrderItemsAdapter(getApplicationContext(), _orderItemsModalList);
        _rvOrderItems.setAdapter(_orderItemsAdapter);
    }

    void _getIntent() {
        Intent orderIntent = getIntent();
        if(orderIntent == null)
            finish();

        Gson gson = new Gson();
        _order = gson.fromJson(getIntent().getStringExtra(Constant.ORDER_INTENT), OrderModal.class);
    }

    void _bindViewsWithID() {
        _rvOrderItems = findViewById(R.id.rv_ordered_items);
        _tvOrderID = findViewById(R.id.tv_order_id);
        _tvOrderStatus = findViewById(R.id.tv_order_status);
        _tvOrderPrice = findViewById(R.id.tv_order_price);
        _tvOrderItemsCount = findViewById(R.id.tv_order_items_count);
        _tvOrderReviews = findViewById(R.id.tv_review);
        _llFeedback = findViewById(R.id.ll_feedback);
        _ratingBar = findViewById(R.id.rating_bar);
    }

}
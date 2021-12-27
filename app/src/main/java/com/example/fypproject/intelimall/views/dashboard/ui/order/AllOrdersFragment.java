package com.example.fypproject.intelimall.views.dashboard.ui.order;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.fypproject.intelimall.R;
import com.example.fypproject.intelimall.adapters.AllOrdersAdapter;
import com.example.fypproject.intelimall.adapters.PendingFeedbacksAdapter;
import com.example.fypproject.intelimall.models.FeedbackModal;
import com.example.fypproject.intelimall.models.OrderModal;
import com.example.fypproject.intelimall.models.UserModal;
import com.example.fypproject.intelimall.network.ApiRequests;
import com.example.fypproject.intelimall.network.Persistent;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllOrdersFragment extends Fragment {
    private RecyclerView _rvAllOrders;
    private LottieAnimationView _loadingAnim;
    private LinearLayout _llNoItems;

    public static ArrayList<OrderModal> _allOrdersModalList;
    public AllOrdersAdapter allOrdersAdapter;
    private UserModal _user;

    public AllOrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_all_orders, container, false);

        // initialize views with ids
        _bindViewsWithID(root);

        // get user data from shared preferences
        _user = Persistent.getLoggedInUser(getContext());

        _populateAllOrders();

        return root;

    }

    private void _populateAllOrders() {
        _rvAllOrders.setHasFixedSize(true);
        //set a vertical layout so the list is displayed top down
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        _rvAllOrders.setLayoutManager(layoutManager);

        // configuring recycler view
        _allOrdersModalList = new ArrayList<>();

        Call<List<OrderModal>> allOrdersModalCall = ApiRequests.getApiService().getAllOrders(_user.getId() + "");
        allOrdersModalCall.enqueue(new Callback<List<OrderModal>>() {
            @Override
            public void onResponse(Call<List<OrderModal>> call, Response<List<OrderModal>> response) {
                Log.e("ALL_ORDERS", call.request().url().toString());
                List<OrderModal> _order = response.body();
                if(_order != null) {
                    _allOrdersModalList.addAll(_order);

                    Log.e("ALL_ORDERS", _allOrdersModalList.size() + "");

                    // mapping   values to adapter and displaying to UI
                    allOrdersAdapter = new AllOrdersAdapter(getContext(), _allOrdersModalList);
                    _rvAllOrders.setAdapter(allOrdersAdapter);
                }

                _loadingAnim.setVisibility(View.GONE);

                if(_allOrdersModalList.size() == 0)
                    _llNoItems.setVisibility(View.VISIBLE);
                else
                    _rvAllOrders.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<List<OrderModal>> call, Throwable t) {
            }
        });
    }

    void _bindViewsWithID(View view) {
        _rvAllOrders = view.findViewById(R.id.rv_all_orders);
        _loadingAnim = view.findViewById(R.id.animationView);
        _llNoItems = view.findViewById(R.id.ll_no_items);
    }

}
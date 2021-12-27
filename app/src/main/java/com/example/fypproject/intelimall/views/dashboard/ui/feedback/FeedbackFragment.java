package com.example.fypproject.intelimall.views.dashboard.ui.feedback;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.fypproject.intelimall.R;
import com.example.fypproject.intelimall.adapters.AllProductsAdapter;
import com.example.fypproject.intelimall.adapters.CategoryButtonAdapter;
import com.example.fypproject.intelimall.adapters.PendingFeedbacksAdapter;
import com.example.fypproject.intelimall.models.CategoryButton;
import com.example.fypproject.intelimall.models.FeedbackModal;
import com.example.fypproject.intelimall.models.ProductModal;
import com.example.fypproject.intelimall.models.UserModal;
import com.example.fypproject.intelimall.network.ApiRequests;
import com.example.fypproject.intelimall.network.Persistent;
import com.example.fypproject.intelimall.utils.Constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackFragment extends Fragment {
    private RecyclerView _rvFeedbacks;
    private LottieAnimationView _loadingAnim;
    private LinearLayout _llNoItems;

    public static ArrayList<FeedbackModal> _pendingFeedbacksModalArrayList;
    public PendingFeedbacksAdapter pendingFeedbacksAdapter;
    private UserModal _user;

    public FeedbackFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_feedback, container, false);

        // initialize views with ids
        _bindViewsWithID(root);

        // get user data from shared preferences
        _user = Persistent.getLoggedInUser(getContext());

        _populatePendingFeedbacks();

        return root;
    }

    private void _populatePendingFeedbacks() {
        _rvFeedbacks.setHasFixedSize(true);
        //set a vertical layout so the list is displayed top down
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        _rvFeedbacks.setLayoutManager(layoutManager);

        // configuring recycler view
        _pendingFeedbacksModalArrayList = new ArrayList<>();

        Call<List<FeedbackModal>> pendingFeedbacksModalCall = ApiRequests.getApiService().getPendingFeedbacks(_user.getId() + "");
        pendingFeedbacksModalCall.enqueue(new Callback<List<FeedbackModal>>() {
            @Override
            public void onResponse(Call<List<FeedbackModal>> call, Response<List<FeedbackModal>> response) {
                List<FeedbackModal> _feedbacks = response.body();
                if(_feedbacks != null) {
                    _pendingFeedbacksModalArrayList.addAll(_feedbacks);

                    // mapping   values to adapter and displaying to UI
                    pendingFeedbacksAdapter = new PendingFeedbacksAdapter(getContext(), _pendingFeedbacksModalArrayList);
                    _rvFeedbacks.setAdapter(pendingFeedbacksAdapter);
                }

                _loadingAnim.setVisibility(View.GONE);

                if(_pendingFeedbacksModalArrayList.size() == 0)
                    _llNoItems.setVisibility(View.VISIBLE);
                else
                    _rvFeedbacks.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<List<FeedbackModal>> call, Throwable t) {

            }
        });
    }

    void _bindViewsWithID(View view) {
        _rvFeedbacks = view.findViewById(R.id.rv_pending_feedbacks);
        _loadingAnim = view.findViewById(R.id.animationView);
        _llNoItems = view.findViewById(R.id.ll_no_items);
    }
}
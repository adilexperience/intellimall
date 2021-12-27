package com.example.fypproject.intelimall.views.dashboard.ui.recommendation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.fypproject.intelimall.R;
import com.example.fypproject.intelimall.adapters.AllProductsAdapter;
import com.example.fypproject.intelimall.adapters.ShoppingCartAdapter;
import com.example.fypproject.intelimall.models.CartItemModal;
import com.example.fypproject.intelimall.models.InterestsModal;
import com.example.fypproject.intelimall.models.ProductModal;
import com.example.fypproject.intelimall.models.UserModal;
import com.example.fypproject.intelimall.network.ApiRequests;
import com.example.fypproject.intelimall.network.Persistent;
import com.example.fypproject.intelimall.utils.Constant;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecommendationFragment extends Fragment {

    // views
    private GridView _gvProducts;
    private LinearLayout _noRecommendations;
    private RelativeLayout _rlHaveRecommendations;
    private LottieAnimationView _loadingAnim;

    // variables
    private UserModal _user;

    private ArrayList<ProductModal> _allProductsModalArrayList;
    private ArrayList<InterestsModal> _allInterestsOfUserArrayList;
    private AllProductsAdapter _allProductsAdapter;

    public RecommendationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_recommendation, container, false);

        // initialize views with ids
        _bindViewsWithID(root);

        // get user data from shared preferences
        _user = Persistent.getLoggedInUser(getContext());

        _getUserInterests();

        return root;
    }

    private void _getUserInterests() {
        // configuring recycler view
        _allInterestsOfUserArrayList = new ArrayList<>();

        Call<List<InterestsModal>> allInterestsModalCall = ApiRequests.getApiService().userInterests(_user.getId() + "");
        allInterestsModalCall.enqueue(new Callback<List<InterestsModal>>() {
            @Override
            public void onResponse(Call<List<InterestsModal>> call, Response<List<InterestsModal>> response) {
                List<InterestsModal> _interests = response.body();
                if(_interests != null) {
                    _allInterestsOfUserArrayList.addAll(_interests);
                }

                if(_allInterestsOfUserArrayList.size() == 0) {
                    _noRecommendations.setVisibility(View.VISIBLE);
                    _loadingAnim.setVisibility(View.GONE);
                    _rlHaveRecommendations.setVisibility(View.GONE);
                }else {
                    _noRecommendations.setVisibility(View.GONE);
                    _populateProducts();
                }
            }

            @Override
            public void onFailure(Call<List<InterestsModal>> call, Throwable t) {

            }
        });
    }

    private void _bindViewsWithID(View root) {
        _gvProducts = root.findViewById(R.id.gv_products);
        _noRecommendations = root.findViewById(R.id.ll_no_items);
        _rlHaveRecommendations = root.findViewById(R.id.rl_has_recommendations);
        _loadingAnim = root.findViewById(R.id.animationView);
    }

    private void _populateProducts() {
        // configuring recycler view
        _allProductsModalArrayList = new ArrayList<>();

        if(_allInterestsOfUserArrayList.size() != 0) {
            StringBuilder _interestedCategories = new StringBuilder();
            for (int i = 0; i < _allInterestsOfUserArrayList.size(); i++) {
                _interestedCategories.append("category=");
                _interestedCategories.append(_allInterestsOfUserArrayList.get(i).getInterests().trim());
                if(i != _allInterestsOfUserArrayList.size()-1)
                    _interestedCategories.append("&");
            }

            OkHttpClient client = new OkHttpClient();

            String url = Constant.RECOMMENDATION_BASE_URL + "recomendation?" + _interestedCategories.toString();
//            Log.e("RECOMMENDATION", url);

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {

                }

                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) {
                    if (response.isSuccessful()) {

                        ResponseBody _product = response.body();
                        Gson gson = new Gson();
                        try {
                            ProductModal[] responseResult = gson.fromJson(_product.string(), ProductModal[].class);
                            _allProductsModalArrayList.addAll(Arrays.asList(responseResult));

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // mapping   values to adapter and displaying to UI
                                    _allProductsAdapter = new AllProductsAdapter(getContext(), _allProductsModalArrayList);
                                    _gvProducts.setAdapter(_allProductsAdapter);

                                    _loadingAnim.setVisibility(View.GONE);
                                    _rlHaveRecommendations.setVisibility(View.VISIBLE);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

}
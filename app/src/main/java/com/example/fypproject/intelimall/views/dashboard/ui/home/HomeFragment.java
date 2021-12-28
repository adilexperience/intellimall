package com.example.fypproject.intelimall.views.dashboard.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.fypproject.intelimall.R;

import com.example.fypproject.intelimall.adapters.AllProductsAdapter;
import com.example.fypproject.intelimall.adapters.CategoryButtonAdapter;
import com.example.fypproject.intelimall.adapters.ProductAdapter;
import com.example.fypproject.intelimall.models.CategoryButton;
import com.example.fypproject.intelimall.models.LoginModal;
import com.example.fypproject.intelimall.models.ProductModal;
import com.example.fypproject.intelimall.models.UserModal;
import com.example.fypproject.intelimall.network.ApiRequests;
import com.example.fypproject.intelimall.network.Persistent;
import com.example.fypproject.intelimall.utils.Constant;
import com.example.fypproject.intelimall.views.authentication.LoginActivity;
import com.example.fypproject.intelimall.views.dashboard.Dashboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    GridView _gvProducts;
    RecyclerView _rvCategories;
    private LottieAnimationView _loadingAnim;

    public static List<CategoryButton> categoryButtonsList = Arrays.asList(new CategoryButton(1, R.string.all, Constant.ALL, true), new CategoryButton(2, R.string.beverages, Constant.BEVERAGES, false), new CategoryButton(3, R.string.bread, Constant.BREAD, false), new CategoryButton(4, R.string.canned_goods, Constant.CANNED_GOODS, false), new CategoryButton(5, R.string.dairy, Constant.DAIRY, false), new CategoryButton(6, R.string.dry_goods, Constant.DRY_GOODS, false), new CategoryButton(7, R.string.frozen_foods, Constant.FROZEN_FOODS, false), new CategoryButton(8, R.string.meat, Constant.MEAT, false));
    public static ArrayList<CategoryButton> categoryButtonsArrayList;
    public static ArrayList<ProductModal> _allProductsModalArrayList, _filteredProductsModalArrayList;
    public static CategoryButton _selectedCategoryButton = new CategoryButton(1, R.string.all, Constant.ALL, true);
    private CategoryButtonAdapter categoryButtonAdapter;
    public static AllProductsAdapter allProductsAdapter;

    public static void updateSelectedCard(int id) {
        _selectedCategoryButton = categoryButtonsArrayList.get(Constant.getCategoryItemIndex(categoryButtonsArrayList, id));
        _updateUI(_selectedCategoryButton);
    }

    private static void _updateUI(CategoryButton selectedCategoryButton) {
        // removing previous data to prevent duplication
        _filteredProductsModalArrayList.clear();
        if(!(selectedCategoryButton.getValue().trim().equalsIgnoreCase(Constant.ALL))) {
            for (ProductModal productModal : _allProductsModalArrayList) {
                if(productModal.getCategory().trim().equalsIgnoreCase(selectedCategoryButton.getValue().trim())){
                    _filteredProductsModalArrayList.add(productModal);
                }
            }
        }else {
            _filteredProductsModalArrayList.addAll(_allProductsModalArrayList);
        }
        allProductsAdapter.notifyDataSetChanged();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // initialize views with ids
        _bindViewsWithID(root);

        _populateHorizontalListData();

        _populateProducts();

        return root;
    }

    private void _bindViewsWithID(View root) {
        _gvProducts = root.findViewById(R.id.gv_products);
        _rvCategories = root.findViewById(R.id.rv_categories);
        _loadingAnim = root.findViewById(R.id.animationView);
    }

    private void _populateProducts() {
        // configuring recycler view
        _allProductsModalArrayList = new ArrayList<>();
        _filteredProductsModalArrayList = new ArrayList<>();

        Call<List<ProductModal>> allProductsModalCall = ApiRequests.getApiService().allProducts();
        allProductsModalCall.enqueue(new Callback<List<ProductModal>>() {
            @Override
            public void onResponse(Call<List<ProductModal>> call, Response<List<ProductModal>> response) {
                List<ProductModal> _product = response.body();
                if(_product != null) {
                    for (ProductModal product : _product) {
                        _allProductsModalArrayList.add(product);
                        _filteredProductsModalArrayList.add(product);
                    }

                    // mapping   values to adapter and displaying to UI
                    allProductsAdapter = new AllProductsAdapter(getContext(), _filteredProductsModalArrayList);
                    _gvProducts.setAdapter(allProductsAdapter);
                }

                _loadingAnim.setVisibility(View.GONE);
                _rvCategories.setVisibility(View.VISIBLE);
                _gvProducts.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<List<ProductModal>> call, Throwable t) {
            }
        });
    }

    private void _populateHorizontalListData() {
        // configuring recycler view
        categoryButtonsArrayList = new ArrayList<>();

        _rvCategories.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        _rvCategories.setLayoutManager(layoutManager);

        // adding values to recycler view from common.java
        categoryButtonsArrayList.clear();
        categoryButtonsArrayList.addAll(categoryButtonsList);

        // mapping   values to adapter and displaying to UI
        categoryButtonAdapter = new CategoryButtonAdapter(getContext(), categoryButtonsArrayList);
        _rvCategories.setAdapter(categoryButtonAdapter);
    }

}
package com.example.fypproject.intelimall.views.dashboard.ui.search;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.fypproject.intelimall.R;
import com.example.fypproject.intelimall.adapters.AllProductsAdapter;
import com.example.fypproject.intelimall.models.ProductModal;
import com.example.fypproject.intelimall.network.ApiRequests;
import com.example.fypproject.intelimall.utils.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    //views
    private EditText _etSearchProducts;
    private CardView _cvSearch;
    private GridView _gvProducts;
    private LinearLayout _llNoItems;

    public static ArrayList<ProductModal> _allProductsModalArrayList, _filteredProductsModalArrayList;
    public static AllProductsAdapter allProductsAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        _bindViewsWithID(root);

        _populateProducts();

        _cvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _processSearch();
            }
        });

        return root;
    }

    void _processSearch() {
        String search = _etSearchProducts.getText().toString().trim();

        if(_allProductsModalArrayList.size() == 0)
            Constant.showSnackBar(getActivity(), "we are getting things ready for you. please wait !");
        else{
            _filteredProductsModalArrayList.clear();
            for (ProductModal productModal : _allProductsModalArrayList) {
                if(productModal.getTitle().toLowerCase().contains(search))
                    _filteredProductsModalArrayList.add(productModal);
            }

            if(_filteredProductsModalArrayList.size() == 0) {
                _gvProducts.setVisibility(View.GONE);
                _llNoItems.setVisibility(View.VISIBLE);
            }else {
                _gvProducts.setVisibility(View.VISIBLE);
                _llNoItems.setVisibility(View.GONE);
            }

            allProductsAdapter.notifyDataSetChanged();
        }
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
                    _allProductsModalArrayList.addAll(_product);

                    // mapping   values to adapter and displaying to UI
                    allProductsAdapter = new AllProductsAdapter(getContext(), _filteredProductsModalArrayList);
                    _gvProducts.setAdapter(allProductsAdapter);
                    _gvProducts.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<ProductModal>> call, Throwable t) {
            }
        });
    }

    void _bindViewsWithID(View view) {
        _etSearchProducts = view.findViewById(R.id.et_search_products);
        _cvSearch = view.findViewById(R.id.cv_search);
        _gvProducts = view.findViewById(R.id.gv_products);
        _llNoItems = view.findViewById(R.id.ll_no_items);
    }

}
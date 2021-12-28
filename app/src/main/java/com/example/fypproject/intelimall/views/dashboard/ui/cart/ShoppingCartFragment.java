package com.example.fypproject.intelimall.views.dashboard.ui.cart;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.fypproject.intelimall.adapters.AllProductsAdapter;
import com.example.fypproject.intelimall.adapters.ShoppingCartAdapter;
import com.example.fypproject.intelimall.models.CartItemModal;
import com.example.fypproject.intelimall.models.PlaceOrderModal;
import com.example.fypproject.intelimall.models.ProductModal;
import com.example.fypproject.intelimall.models.UpdateCartItemModal;
import com.example.fypproject.intelimall.models.UserModal;
import com.example.fypproject.intelimall.network.ApiRequests;
import com.example.fypproject.intelimall.network.Persistent;
import com.example.fypproject.intelimall.utils.Constant;
import com.example.fypproject.intelimall.R;
import com.example.fypproject.intelimall.adapters.ItemsCustomeAdpater;
import com.example.fypproject.intelimall.views.dashboard.Dashboard;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingCartFragment extends Fragment {

    // views
    private LottieAnimationView _loadingAnim;
    public static LinearLayout _llNoResults;
    public static RelativeLayout _rlHaveCartItems;
    private RecyclerView _rvCartItems;
    public static TextView _tvItemsInCartAndTotalPrice;
    private TextView _tvEmptyCart;
    private CardView _cvOrderNow;

    // variables
    public static String totalPrice;
    private UserModal _user;
    public static ArrayList<CartItemModal> _cartItemsModalList;
    public static ShoppingCartAdapter shoppingCartAdapter;

    public ShoppingCartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);

        // initialize views with ids
        _bindViewsWithID(view);

        _user = Persistent.getLoggedInUser(getContext());

        _populateCartItems();

        _tvEmptyCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("SHOPPING_CART", _cartItemsModalList.size() + "");
                _loadingAnim.setVisibility(View.VISIBLE);
                for (CartItemModal cartItemModal : _cartItemsModalList) {
                    UpdateCartItemModal updateCartItemModal = new UpdateCartItemModal(0, cartItemModal.getUser_id(), cartItemModal.getProduct_id());
                    Call<Void> updateCartCall = ApiRequests.getApiService().updateCartItem(updateCartItemModal);
                    updateCartCall.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            _cartItemsModalList.clear();
                            _rlHaveCartItems.setVisibility(View.GONE);
                            _llNoResults.setVisibility(View.VISIBLE);
                            _loadingAnim.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            _loadingAnim.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });

        _cvOrderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _placeOrder();
            }
        });

        return view;
    }

    void _placeOrder() {
        _loadingAnim.setVisibility(View.VISIBLE);


        Date currentTime = Calendar.getInstance().getTime();
        PlaceOrderModal placeOrderModal = new PlaceOrderModal(_user.getId()+"", totalPrice, currentTime.toString(), _cartItemsModalList);
        Call<Void> placeOrder = ApiRequests.getApiService().placeOrder(placeOrderModal);
        placeOrder.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                _loadingAnim.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Order Placed", Toast.LENGTH_SHORT).show();
                Constant.moveIntent(getActivity(), Dashboard.class, true);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                _loadingAnim.setVisibility(View.GONE);
            }
        });
    }

    void _populateCartItems() {

        _rvCartItems.setHasFixedSize(true);
        //set a vertical layout so the list is displayed top down
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        _rvCartItems.setLayoutManager(layoutManager);

        // configuring recycler view
        _cartItemsModalList = new ArrayList<>();

        Log.e("SHOPPING_CART", _user.getId() + "");
        Call<List<CartItemModal>> cartItemsModal = ApiRequests.getApiService().getCartItems(_user.getId()+"");
        cartItemsModal.enqueue(new Callback<List<CartItemModal>>() {
            @Override
            public void onResponse(Call<List<CartItemModal>> call, Response<List<CartItemModal>> response) {
                List<CartItemModal> _cartItems = response.body();
                if(_cartItems != null) {
                    _cartItemsModalList.addAll(_cartItems);


                    // mapping   values to adapter and displaying to UI
                    shoppingCartAdapter = new ShoppingCartAdapter(getContext(), _cartItemsModalList);
                    _rvCartItems.setAdapter(shoppingCartAdapter);
                }

                _loadingAnim.setVisibility(View.GONE);
                if(_cartItemsModalList.size() == 0)
                    _llNoResults.setVisibility(View.VISIBLE);
                else {
                    updateCartInformation(_cartItemsModalList);
                    _rlHaveCartItems.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<List<CartItemModal>> call, Throwable t) {
                Log.e("SHOPPING_CART", t.getMessage());
            }
        });
    }

    public static void updateCartInformation(List<CartItemModal> list) {
        int _inCartProductsCount = list.size();
        double _totalPrice = 0;
        for (CartItemModal cartItemModal : list) {
            int productQuantity = cartItemModal.getQuantity();
            double productPrice = Double.parseDouble(cartItemModal.getProduct().getPrice());

            _totalPrice += productQuantity * productPrice;
        }
        totalPrice = _totalPrice + "";
        _tvItemsInCartAndTotalPrice.setText("("+ _inCartProductsCount + " Items) In Cart, Total Price: " + _totalPrice + " PKR");
        if(_inCartProductsCount == 0) {
            _rlHaveCartItems.setVisibility(View.GONE);
            _llNoResults.setVisibility(View.VISIBLE);
        }
    }

    void _bindViewsWithID(View view) {
        _loadingAnim = view.findViewById(R.id.animationView);
        _llNoResults = view.findViewById(R.id.ll_no_items);
        _rlHaveCartItems = view.findViewById(R.id.rl_has_cart_items);
        _rvCartItems = view.findViewById(R.id.rv_cart_items);
        _tvItemsInCartAndTotalPrice = view.findViewById(R.id.tv_items_in_cart_and_total_price);
        _tvEmptyCart = view.findViewById(R.id.tv_empty_cart);
        _cvOrderNow = view.findViewById(R.id.cv_order_now);
    }

}
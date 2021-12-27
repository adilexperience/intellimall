package com.example.fypproject.intelimall.views.dashboard.ui.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.fypproject.intelimall.R;
import com.example.fypproject.intelimall.adapters.AllProductsAdapter;
import com.example.fypproject.intelimall.models.AddItemInCartModal;
import com.example.fypproject.intelimall.models.CartItemModal;
import com.example.fypproject.intelimall.models.ProductModal;
import com.example.fypproject.intelimall.models.UpdateCartItemModal;
import com.example.fypproject.intelimall.models.UserModal;
import com.example.fypproject.intelimall.network.ApiRequests;
import com.example.fypproject.intelimall.network.Persistent;
import com.example.fypproject.intelimall.utils.Constant;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    //views
    private ImageView _ivProductHeader;
    private TextView _tvProductTitle, _tvProductDescription, _tvProductPrice, _tvProductCategory, _tvQuantity;
    private CardView _cvAddToCart, _cvAddItem, _cvRemoveItem;
    private LottieAnimationView _animationView;

    // variables
    public static ProductModal _product;
    private CartItemModal _cartItemModal;
    public UserModal _user;
    private boolean isProductInCart = false;
    private int _quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // initialize views with ids
        _bindViewsWithID();

        // get user data from shared preferences
        _user = Persistent.getLoggedInUser(getApplicationContext());

        // getting data from intent and making horizontal buttons lists as per available data
        _getIntent();

        // mapping values to UI
        _mappingValuesOnUI();

        _checkIfProductIsInCartOrNot();

        _cvAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _tvQuantity.setText(++_quantity + "");
            }
        });

        _cvRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(_quantity != 1) _tvQuantity.setText(--_quantity +"");
                else
                    Constant.showSnackBar(ProductDetailActivity.this, "At-least 1 item is required for cart");
            }
        });

        _cvAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _animationView.setVisibility(View.VISIBLE);

                // add or update cart item
                if(isProductInCart) {
                    UpdateCartItemModal updateCartItemModal = new UpdateCartItemModal(_cartItemModal.getQuantity() + _quantity, _cartItemModal.getUser_id(), _cartItemModal.getProduct_id());
                    Call<Void> updateCartCall = ApiRequests.getApiService().updateCartItem(updateCartItemModal);
                    updateCartCall.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            _animationView.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Item quantity increased", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            _animationView.setVisibility(View.GONE);
                        }
                    });
                }else{
                    Date currentTime = Calendar.getInstance().getTime();
                    AddItemInCartModal addItemInCartModal = new AddItemInCartModal(_product.getId(), _quantity, _user.getId(), currentTime.toString());
                    Call<Void> addItemInCartCall = ApiRequests.getApiService().addItemInCart(addItemInCartModal);
                    addItemInCartCall.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            _checkIfProductIsInCartOrNot();
                            Toast.makeText(getApplicationContext(), "Item Added to cart", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            _animationView.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    private void _mappingValuesOnUI() {
        if(_product.getImage_url().startsWith("data")) {
            // convert data base64 to image
            String base64String = _product.getImage_url();
            String base64Image = base64String.split(",")[1];
            byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            _ivProductHeader.setImageBitmap(decodedByte);
        }else
            Picasso.get().load(_product.getImage_url()).into(_ivProductHeader);

        _tvProductTitle.setText(_product.getTitle());
        _tvProductDescription.setText(_product.getDescription());
        _tvProductPrice.setText(_product.getPrice());
        _tvQuantity.setText(_quantity + "");
        _tvProductCategory.setText(_product.getCategory());

    }

    private void _checkIfProductIsInCartOrNot() {
            Call<List<CartItemModal>> allCartItemsCall = ApiRequests.getApiService().getCartItems(_user.getId() + "");
            allCartItemsCall.enqueue(new Callback<List<CartItemModal>>() {
                @Override
                public void onResponse(Call<List<CartItemModal>> call, Response<List<CartItemModal>> response) {
                    List<CartItemModal> _cartItems = response.body();
                    if(_cartItems != null) {
                        for (CartItemModal cartItemModal : _cartItems) {
                            isProductInCart = cartItemModal.getProduct_id() == _product.getId();
                            if(isProductInCart) {
                                _cartItemModal = cartItemModal;
                                break;
                            }
                        }

                        _animationView.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<List<CartItemModal>> call, Throwable t) {

                }
            });
    }

    void _bindViewsWithID() {
        _ivProductHeader = findViewById(R.id.iv_product_header);
        _tvProductTitle = findViewById(R.id.tv_product_title);
        _tvProductDescription = findViewById(R.id.tv_product_description);
        _tvProductPrice = findViewById(R.id.tv_product_price);
        _tvProductCategory = findViewById(R.id.tv_product_category);
        _tvQuantity = findViewById(R.id.tv_quantity);
        _cvAddItem = findViewById(R.id.cv_add_item);
        _cvRemoveItem = findViewById(R.id.cv_remove_item);
        _cvAddToCart = findViewById(R.id.cv_add_to_cart);
        _animationView = findViewById(R.id.animationView);
    }

    void _getIntent() {
        Intent productIntent = getIntent();
        if(productIntent == null)
            finish();

        Gson gson = new Gson();
        _product = gson.fromJson(getIntent().getStringExtra(Constant.PRODUCT_INTENT), ProductModal.class);
    }
}
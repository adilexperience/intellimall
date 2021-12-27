package com.example.fypproject.intelimall.network;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.fypproject.intelimall.models.AddItemInCartModal;
import com.example.fypproject.intelimall.models.CartItemModal;
import com.example.fypproject.intelimall.models.FeedbackModal;
import com.example.fypproject.intelimall.models.FeedbackRequestModal;
import com.example.fypproject.intelimall.models.InterestsModal;
import com.example.fypproject.intelimall.models.LoginModal;
import com.example.fypproject.intelimall.models.OrderModal;
import com.example.fypproject.intelimall.models.PlaceOrderModal;
import com.example.fypproject.intelimall.models.ProductModal;
import com.example.fypproject.intelimall.models.RegisterRequestModal;
import com.example.fypproject.intelimall.models.RegisterResponseModal;
import com.example.fypproject.intelimall.models.UpdateCartItemModal;
import com.example.fypproject.intelimall.models.UpdateUserModal;
import com.example.fypproject.intelimall.models.UserModal;
import com.example.fypproject.intelimall.utils.Constant;
import com.example.fypproject.intelimall.views.authentication.LoginActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class ApiRequests {
    public static ApiService apiService = null;
    public static ApiService apiRecommendationService = null;

    public static ApiService getApiService() {
        if(apiService == null) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(Constant.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }

    public static ApiService getRecommendationApiService() {
        if(apiRecommendationService == null) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(Constant.RECOMMENDATION_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
            apiRecommendationService = retrofit.create(ApiService.class);
        }
        return apiRecommendationService;
    }

    public interface ApiService {
        @GET("/user/login/{EMAIL}/{PASSWORD}")
        Call<LoginModal> userLogin(@Path("EMAIL") String email, @Path("PASSWORD") String uid);

        @GET("/getUser/{USER_ID}")
        Call<UserModal> getUser(@Path("USER_ID") String userID);

        @POST("/user")
        Call<RegisterResponseModal> userRegistration(@Body RegisterRequestModal registerUser);

        @POST("/cart")
        Call<Void> addItemInCart(@Body AddItemInCartModal addItemInCartModal);

        @POST("/order")
        Call<Void> placeOrder(@Body PlaceOrderModal placeOrderModal);

        @GET("/product")
        Call<List<ProductModal>> allProducts();

        @GET("/feedback/{user_id}/{order_id}")
        Call<FeedbackModal> orderFeedback(@Path("user_id") String user_id, @Path("order_id") String order_id);

        @GET("/cart/{USER_ID}")
        Call<List<CartItemModal>> getCartItems(@Path("USER_ID") String userId);

        @GET("/feedbackuser/{USER_ID}")
        Call<List<FeedbackModal>> getPendingFeedbacks(@Path("USER_ID") String userId);

        @GET("/recomendation/{CATEGORY}")
        Call<List<ProductModal>> recommendedProducts(@Path("CATEGORY") String category);

        @GET("/interests/{USER_ID}")
        Call<List<InterestsModal>> userInterests(@Path("USER_ID") String userId);

        @GET("/order/user/{USER_ID}")
        Call<List<OrderModal>> getAllOrders(@Path("USER_ID") String userId);

        @PUT("/user/{USER_ID}")
        Call<Void> updateUser(@Path("USER_ID") String userID, @Body UpdateUserModal updatedUser);

        @PUT("/cart")
        Call<Void> updateCartItem(@Body UpdateCartItemModal updatedCartItem);

        @PUT("/feedback")
        Call<Void> updateFeedback(@Body FeedbackRequestModal updatedUser);
    }

    public static void logout(Context context, Activity currentActivity) {
        Persistent.removePersistentData(context);
        Constant.moveIntent(currentActivity, LoginActivity.class, true);
    }
}

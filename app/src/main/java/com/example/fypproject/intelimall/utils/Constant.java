package com.example.fypproject.intelimall.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.fypproject.intelimall.models.CategoryButton;
import com.example.fypproject.intelimall.models.FeedbackModal;
import com.example.fypproject.intelimall.models.OrderItemModal;
import com.example.fypproject.intelimall.models.OrderModal;
import com.example.fypproject.intelimall.models.ProductModal;
import com.example.fypproject.intelimall.views.dashboard.ui.order.OrderDetailActivity;
import com.example.fypproject.intelimall.views.dashboard.ui.product.ProductDetailActivity;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Constant {
   public static final String PERSISTENT = "Persistent";
   public static final String PRODUCT_INTENT = "PRODUCT";
   public static final String ORDER_INTENT = "ORDER";
   public static final String LOGGED_IN_USER = "LoggedInUser";
    public static final String BASE_URL = "https://intelli-mall.herokuapp.com/";
    public static final String RECOMMENDATION_BASE_URL = "https://intellimall-python.herokuapp.com/";

    public static final String ALL = "all";
    public static final String BEVERAGES = "beverages";
    public static final String BREAD = "bread";
    public static final String CANNED_GOODS = "canned goods";
    public static final String DAIRY = "dairy";
    public static final String DRY_GOODS = "dry goods";
    public static final String FROZEN_FOODS = "frozen foods";
    public static final String MEAT = "meat";
    public static final String PRODUCT = "product";


    public static List<ProductModal> globalArraylist=new ArrayList<>();

    public static int getCategoryItemIndex(ArrayList<CategoryButton> categoryButtonArrayList, int id) {
        int _index = 0;
        for(int i = 0; i < categoryButtonArrayList.size(); i++) {
            if(categoryButtonArrayList.get(i).getId() == id)
                _index = i;
        }
        return _index;
    }

    public static int getFeedbackIndex(ArrayList<FeedbackModal> feedbackModalArrayList, int id) {
        int _index = 0;
        for(int i = 0; i < feedbackModalArrayList.size(); i++) {
            if(feedbackModalArrayList.get(i).getId() == id)
                _index = i;
        }
        return _index;
    }

   public static void moveIntent(Activity currentActivity, Class destinationClass, boolean shouldFinish) {
      currentActivity.startActivity(new Intent(currentActivity, destinationClass));
      if(shouldFinish)
         currentActivity.finish();
   }

    public static void showSnackBar(Activity activity, String message) {
        Snackbar.make(activity.getWindow().getDecorView().getRootView(), message, BaseTransientBottomBar.LENGTH_SHORT).show();
    }

    public static void openProductDetailsUI(Context context, ProductModal product) {
        Intent _intent = new Intent(context, ProductDetailActivity.class);
        Gson gson = new Gson();
        String _fixation = gson.toJson(product);
        _intent.putExtra(Constant.PRODUCT_INTENT, _fixation);
        _intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(_intent);
    }

    public static void openOrderDetailUI(Context context, OrderModal order) {
        Intent _intent = new Intent(context, OrderDetailActivity.class);
        Gson gson = new Gson();
        String _fixation = gson.toJson(order);
        _intent.putExtra(Constant.ORDER_INTENT, _fixation);
        _intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(_intent);
    }
}

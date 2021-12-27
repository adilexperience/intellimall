package com.example.fypproject.intelimall.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.fypproject.intelimall.R;
import com.example.fypproject.intelimall.models.UserModal;
import com.example.fypproject.intelimall.network.ApiRequests;
import com.example.fypproject.intelimall.network.Persistent;
import com.example.fypproject.intelimall.utils.Constant;
import com.example.fypproject.intelimall.views.authentication.LoginActivity;
import com.example.fypproject.intelimall.views.dashboard.Dashboard;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //starting timer to move to intro activity
        processTimer();

    }

    private void processTimer() {
        // check if user available in shared preferences
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Persistent.isUserLoggedIn(getApplicationContext())) {
                    UserModal _user = Persistent.getLoggedInUser(getApplicationContext());
                    Call<UserModal> getUserModalCall = ApiRequests.getApiService().getUser(_user.getId() + "");
                    getUserModalCall.enqueue(new Callback<UserModal>() {
                        @Override
                        public void onResponse(Call<UserModal> call, Response<UserModal> response) {
                            UserModal user = response.body();
                            if(user != null) {
                                if(user.getIs_allowed_in_app() == 0) Constant.moveIntent(SplashActivity.this, LockedActivity.class, true);
                                else Constant.moveIntent(SplashActivity.this, Dashboard.class, true);
                            }
                        }

                        @Override
                        public void onFailure(Call<UserModal> call, Throwable t) {

                        }
                    });
                }
                else
                    Constant.moveIntent(SplashActivity.this, LoginActivity.class, true);
            }
        }, 2500);
    }
}
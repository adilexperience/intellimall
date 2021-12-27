package com.example.fypproject.intelimall.views.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.fypproject.intelimall.R;
import com.example.fypproject.intelimall.models.LoginModal;
import com.example.fypproject.intelimall.network.ApiRequests;
import com.example.fypproject.intelimall.network.Persistent;
import com.example.fypproject.intelimall.utils.Constant;
import com.example.fypproject.intelimall.views.dashboard.Dashboard;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    // views
    private LinearLayout _llNotHaveAccount;
    private Button _btnLogin;
    private EditText _etMail, _etUid;
    private LottieAnimationView _loadingAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // binding views with ids
        _bindViewsWithIDS();

        _btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _processLogin();
            }
        });

        _llNotHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.moveIntent(LoginActivity.this, RegisterActivity.class, false);
            }
        });

    }

    void _processLogin() {
        String _email = _etMail.getText().toString().trim();
        String _uid = _etUid.getText().toString().trim();

        if((_email.length() == 0) || !(Patterns.EMAIL_ADDRESS.matcher(_email).matches()))
            Constant.showSnackBar(LoginActivity.this, "Enter valid email-address and try again");
        else if(_uid.length() == 0)
            Constant.showSnackBar(LoginActivity.this, "Enter password and try again");
        else {
            // make api call
            _loadingAnim.setVisibility(View.VISIBLE);

            Call<LoginModal> loginModalCall = ApiRequests.getApiService().userLogin(_email, _uid);
            loginModalCall.enqueue(new Callback<LoginModal>() {
                @Override
                public void onResponse(Call<LoginModal> call, Response<LoginModal> response) {
                    LoginModal _modal = response.body();
                    _loadingAnim.setVisibility(View.GONE);

                    if(_modal.isStatus()) {
                        Persistent.persistLoggedInUser(getApplicationContext(), _modal.getUser()); // save user object in shared preference and move intent to dashboard
                        Constant.moveIntent(LoginActivity.this, Dashboard.class, true);
                    }else
                        Constant.showSnackBar(LoginActivity.this, _modal.getMessage()); // display error message and let user try again
                }

                @Override
                public void onFailure(Call<LoginModal> call, Throwable t) {
                    _loadingAnim.setVisibility(View.GONE);
                }
            });
        }

    }

    void _bindViewsWithIDS() {
        _llNotHaveAccount = findViewById(R.id.ll_not_have_account);
        _btnLogin = findViewById(R.id.btn_login);
        _etMail = findViewById(R.id.et_mail);
        _etUid = findViewById(R.id.et_uid);
        _loadingAnim = findViewById(R.id.animationView);
    }
}
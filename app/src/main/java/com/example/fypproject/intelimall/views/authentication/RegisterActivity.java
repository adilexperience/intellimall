package com.example.fypproject.intelimall.views.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.fypproject.intelimall.R;
import com.example.fypproject.intelimall.models.LoginModal;
import com.example.fypproject.intelimall.models.RegisterRequestModal;
import com.example.fypproject.intelimall.models.RegisterResponseModal;
import com.example.fypproject.intelimall.models.UserModal;
import com.example.fypproject.intelimall.network.ApiRequests;
import com.example.fypproject.intelimall.network.Persistent;
import com.example.fypproject.intelimall.utils.Constant;
import com.example.fypproject.intelimall.views.dashboard.Dashboard;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    // views
    private EditText _etUsername, _etEmailAddress, _etUID, _etPhone, _etAddress;
    private Button _btnRegister;
    private LinearLayout _llHaveAnAccount;
    private LottieAnimationView _loadingAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // bind views with ids
        _bindViewsWithID();

        _btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             _processRegistration();
            }
        });

        _llHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void _processRegistration() {
        String _username = _etUsername.getText().toString().trim();
        String _phone = _etPhone.getText().toString().trim();
        String _address = _etAddress.getText().toString().trim();
        String _email = _etEmailAddress.getText().toString().trim();
        String _uid = _etUID.getText().toString().trim();

        if(_username.isEmpty())
            Constant.showSnackBar(RegisterActivity.this, "Enter username and try again");
        else if(_phone.isEmpty() || _phone.length() < 11)
            Constant.showSnackBar(RegisterActivity.this, "Enter valid phone number and try again");
        else if(_address.isEmpty())
            Constant.showSnackBar(RegisterActivity.this, "Enter address and try again");
        else if((_email.length() == 0) || !(Patterns.EMAIL_ADDRESS.matcher(_email).matches()))
            Constant.showSnackBar(RegisterActivity.this, "Enter valid email-address and try again");
        else if(_uid.length() == 0 || _uid.length() < 8)
            Constant.showSnackBar(RegisterActivity.this, "Enter strong password and try again");
        else {
            // make api call
            _loadingAnim.setVisibility(View.VISIBLE);

            Date currentTime = Calendar.getInstance().getTime();
            RegisterRequestModal _registerRequestModal = new RegisterRequestModal(_username, _email, _uid, _phone, _address, currentTime.toString(), 1);

            Call<RegisterResponseModal> _registerResponseModalCall = ApiRequests.getApiService().userRegistration(_registerRequestModal);
            _registerResponseModalCall.enqueue(new Callback<RegisterResponseModal>() {
                @Override
                public void onResponse(Call<RegisterResponseModal> call, Response<RegisterResponseModal> response) {
                    RegisterResponseModal _modal = response.body();
                    _loadingAnim.setVisibility(View.GONE);

                    if(_modal.getUser() != null) {
                        Persistent.persistLoggedInUser(getApplicationContext(), _modal.getUser()); // save user object in shared preference and move intent to dashboard
                        Constant.moveIntent(RegisterActivity.this, Dashboard.class, true);
                    }else
                        Constant.showSnackBar(RegisterActivity.this, _modal.getMessage()); // display error message and let user try again
                }

                @Override
                public void onFailure(Call<RegisterResponseModal> call, Throwable t) {
                    _loadingAnim.setVisibility(View.GONE);
                }
            });
        }
    }

    void _bindViewsWithID() {
        _etUsername = findViewById(R.id.et_username);
        _etEmailAddress = findViewById(R.id.et_mail);
        _etUID = findViewById(R.id.et_uid);
        _etAddress = findViewById(R.id.et_address);
        _etPhone = findViewById(R.id.et_phone);
        _btnRegister = findViewById(R.id.btn_signup);
        _llHaveAnAccount = findViewById(R.id.ll_have_account);
        _loadingAnim = findViewById(R.id.animationView);
    }

}
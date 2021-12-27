package com.example.fypproject.intelimall.views.dashboard.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.fypproject.intelimall.R;
import com.example.fypproject.intelimall.models.RegisterRequestModal;
import com.example.fypproject.intelimall.models.RegisterResponseModal;
import com.example.fypproject.intelimall.models.UpdateUserModal;
import com.example.fypproject.intelimall.models.UserModal;
import com.example.fypproject.intelimall.network.ApiRequests;
import com.example.fypproject.intelimall.network.Persistent;
import com.example.fypproject.intelimall.utils.Constant;
import com.example.fypproject.intelimall.views.authentication.RegisterActivity;
import com.example.fypproject.intelimall.views.dashboard.Dashboard;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryFragment extends Fragment {

    // views
    private EditText _etUsername, _etPhone, _etAddress, _etEmail;
    private Button _btnUpdate;

    // variables
    private UserModal _user;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        _bindViewsWithIDS(root);

        _user = Persistent.getLoggedInUser(getContext());

        _btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _processUpdate();
            }
        });

        return root;
    }

    void _bindViewsWithIDS(View root) {
        _etUsername = root.findViewById(R.id.username_et);
        _etPhone = root.findViewById(R.id.phone_et);
        _etAddress = root.findViewById(R.id.address_et);
        _etEmail = root.findViewById(R.id.email_et);
        _btnUpdate = root.findViewById(R.id.button);
    }

    void _processUpdate() {
        String username = _etUsername.getText().toString().trim();
        String phone = _etPhone.getText().toString().trim();
        String address = _etAddress.getText().toString().trim();
        String email = _etEmail.getText().toString().trim();

        if(username.isEmpty() && phone.isEmpty() && address.isEmpty() && email.isEmpty())
            Constant.showSnackBar(getActivity(), "Enter Values in fields before proceeding");
        else {
            if(username.isEmpty()) username = _user.getName();
            if(phone.isEmpty()) phone = _user.getPhone();
            if(address.isEmpty()) address = _user.getAddress();
            if(email.isEmpty()) email = _user.getEmail_address();
            UserModal updatedUser = new UserModal(_user.getId(), _user.getIs_allowed_in_app(), username, email, phone, address, _user.getJoined_at());
            UpdateUserModal user = new UpdateUserModal(username, email, phone, address);

            Call<Void> _updateUserModalCall = ApiRequests.getApiService().updateUser(_user.getId() + "", user);
            _updateUserModalCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Persistent.persistLoggedInUser(getContext(), updatedUser);
                    Constant.moveIntent(getActivity(), Dashboard.class, true);
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                }
            });
        }
    }

}
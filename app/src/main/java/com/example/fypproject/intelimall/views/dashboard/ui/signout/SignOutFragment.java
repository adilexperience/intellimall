package com.example.fypproject.intelimall.views.dashboard.ui.signout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fypproject.intelimall.R;
import com.example.fypproject.intelimall.network.ApiRequests;
import com.example.fypproject.intelimall.views.dashboard.Dashboard;

public class SignOutFragment extends Fragment {

    public SignOutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_out, container, false);

        ApiRequests.logout(getContext(), getActivity());

        return view;
    }
}
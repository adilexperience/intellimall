package com.example.fypproject.intelimall.views.dashboard;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.fypproject.intelimall.R;
import com.example.fypproject.intelimall.models.LoginModal;
import com.example.fypproject.intelimall.models.UserModal;
import com.example.fypproject.intelimall.network.ApiRequests;
import com.example.fypproject.intelimall.network.Persistent;
import com.example.fypproject.intelimall.utils.Constant;
import com.example.fypproject.intelimall.views.LockedActivity;
import com.example.fypproject.intelimall.views.SplashActivity;
import com.example.fypproject.intelimall.views.authentication.LoginActivity;
import com.example.fypproject.intelimall.views.dashboard.ui.cart.ShoppingCartFragment;
import com.example.fypproject.intelimall.views.dashboard.ui.feedback.FeedbackFragment;
import com.example.fypproject.intelimall.views.dashboard.ui.gallery.GalleryFragment;
import com.example.fypproject.intelimall.views.dashboard.ui.home.HomeFragment;
import com.example.fypproject.intelimall.views.dashboard.ui.order.AllOrdersFragment;
import com.example.fypproject.intelimall.views.dashboard.ui.search.SearchFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private UserModal _user;

    // views
    private DrawerLayout drawer;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // binding views with ids
        _bindViewsWithIDS();

        // get user details
        _user = Persistent.getLoggedInUser(getApplicationContext());

        navigationView.setBackgroundColor(getResources().getColor(R.color.white));

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_search, R.id.nav_shopping_cart, R.id.nav_all_orders, R.id.nav_pending_feedbacks, R.id.nav_recommendations, R.id.signout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        updateNavHeader();
    }

    private void _bindViewsWithIDS() {
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void updateNavHeader() {
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.navName);
        TextView navUserMail = headerView.findViewById(R.id.navEmail);
        navUsername.setText(_user.getName());
        navUserMail.setText(_user.getEmail_address());
    }

}
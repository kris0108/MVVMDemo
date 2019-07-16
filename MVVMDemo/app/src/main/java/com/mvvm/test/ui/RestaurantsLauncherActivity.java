package com.mvvm.test.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mvvm.test.R;


public class RestaurantsLauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            RestaurantsListFragment fragment = new RestaurantsListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, RestaurantsListFragment.TAG).commit();
            getSupportFragmentManager().executePendingTransactions();
        }
    }
}

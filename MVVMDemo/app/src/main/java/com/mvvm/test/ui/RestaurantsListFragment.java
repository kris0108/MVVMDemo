package com.mvvm.test.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvvm.test.databinding.RestaurantsListFragBinding;
import com.mvvm.test.R;
import com.mvvm.test.model.Results;
import com.mvvm.test.ui.adapter.RestaurantsAdapter;
import com.mvvm.test.viewmodel.RestaurantsListViewModel;

import java.util.List;

public class RestaurantsListFragment extends Fragment {
    public static final String TAG = "RestaurantsListFragment";
    private RestaurantsAdapter mRestaurantsAdapter;
    private RestaurantsListFragBinding binding;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.restaurants_list_frag, container, false);
        mRecyclerView = binding.restaurantsList;
        mRestaurantsAdapter = new RestaurantsAdapter();
        binding.restaurantsList.setAdapter(mRestaurantsAdapter);
        binding.setIsLoading(true);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final RestaurantsListViewModel viewModel =
                ViewModelProviders.of(this).get(RestaurantsListViewModel.class);

        observeViewModel(viewModel);
    }

    private void observeViewModel(RestaurantsListViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getmResultsObservable().observe(this, new Observer<List<Results>>() {
            @Override
            public void onChanged(@Nullable List<Results> results) {
                if(results != null) {
                    binding.setIsLoading(false);
                    mRestaurantsAdapter.setRestaurantsList(results);
                }
            }
        });
    }
}

package com.mvvm.test;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import com.mvvm.test.model.Results;
import com.mvvm.test.repository.RestaurantsApiRepository;
import com.mvvm.test.viewmodel.RestaurantsListViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TestRestaurantsViewModel {

    @Mock
    Context context;

    @Mock
    Application application;

    @Mock
    Observer<List<Results>> observer;

    LiveData<List<Results>> results;

    RestaurantsApiRepository restaurantsApiRepository;

    private RestaurantsListViewModel restaurantsListViewModel;



    @Before
    public void init() {
        restaurantsListViewModel = new RestaurantsListViewModel(application);
        restaurantsApiRepository = RestaurantsApiRepository.getInstance();
    }

    @Test
    public void viewModelNotNull() {
        assertTrue(restaurantsListViewModel != null);
    }

    @Test
    public void testResultsObservable() {
        restaurantsListViewModel.getmResultsObservable().observeForever(observer);
        results = restaurantsListViewModel.getmResultsObservable();
        assertTrue(results.hasObservers());
    }

    @Test
    public void testRestaurantsRepoWithNull() {
        results = restaurantsApiRepository.populateRestaurants(null,null,null,null);
        assertNull(results.getValue());
    }
}

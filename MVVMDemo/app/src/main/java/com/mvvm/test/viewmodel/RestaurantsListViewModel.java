package com.mvvm.test.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.mvvm.test.model.QueryParameters;
import com.mvvm.test.model.Results;
import com.mvvm.test.repository.RestaurantsApiRepository;

import java.util.List;

public class RestaurantsListViewModel extends AndroidViewModel {

    private LiveData<List<Results>> mResultsObservable;
    private QueryParameters mQueryParameters;

    public RestaurantsListViewModel(@NonNull Application application) {
        super(application);
        mQueryParameters = new QueryParameters();
        mResultsObservable = RestaurantsApiRepository.getInstance().populateRestaurants(
                        mQueryParameters.getLatitude(), mQueryParameters.getLongitude(),
                        mQueryParameters.getOffset(), mQueryParameters.getLimit());
    }

    public LiveData<List<Results>> getmResultsObservable() {
        return mResultsObservable;
    }
}

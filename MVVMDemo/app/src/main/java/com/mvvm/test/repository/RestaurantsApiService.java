package com.mvvm.test.repository;

import com.mvvm.test.model.Results;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestaurantsApiService {

    @GET("/v2/restaurant/")
    //Call<List<Results>> getRestaurants(@QueryMap Map<String, String> params);
    Call<List<Results>> getRestaurants(@Query("lat") String lat,
                                       @Query("lng") String lng,
                                       @Query("offset") String offset,
                                       @Query("limit") String limit);
}

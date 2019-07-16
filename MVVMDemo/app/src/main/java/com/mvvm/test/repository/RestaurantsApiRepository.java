package com.mvvm.test.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.mvvm.test.model.Results;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class RestaurantsApiRepository {

    private RestaurantsApiService mRestaurentApiService;
    private static RestaurantsApiRepository mRestaurentApiRepository;

    private RestaurantsApiRepository() {
        Timber.plant(new Timber.DebugTree());

        HttpLoggingInterceptor httpLoggingInterceptor = new
                HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Timber.i(message);
            }
        });

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.doordash.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mRestaurentApiService = retrofit.create(RestaurantsApiService.class);
    }

    public static RestaurantsApiRepository getInstance() {
        if(null == mRestaurentApiRepository) {
            mRestaurentApiRepository = new RestaurantsApiRepository();
        }
        return mRestaurentApiRepository;
    }

    public LiveData<List<Results>> populateRestaurants(/*Map<String, String> params*/
                                   String lat, String lng, String offset, String limit) {
        final MutableLiveData<List<Results>> data = new MutableLiveData<>();

        mRestaurentApiService.getRestaurants(lat, lng, offset, limit).enqueue(new Callback<List<Results>>() {
            @Override
            public void onResponse(Call<List<Results>> call, Response<List<Results>> response) {
                if(null != response) {
                    Timber.i(response.body().toString());
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Results>> call, Throwable t) {
                Timber.e(t.getMessage());
            }
        });
        return data;
    }

}

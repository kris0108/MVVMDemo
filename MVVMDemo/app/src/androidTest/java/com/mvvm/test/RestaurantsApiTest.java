package com.mvvm.test;

import com.mvvm.test.model.Results;
import com.mvvm.test.repository.RestaurantsApiService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class RestaurantsApiTest {

    private MockWebServer server;

    @Before
    public void setUp() {
        server = new MockWebServer();
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    public void testRestaurantsRepo() throws IOException, InterruptedException {
        final BlockingQueue<String> events = new LinkedBlockingQueue();
        final MockResponse mockResponse = new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"phone_number\":\"+16505613027\",\"yelp_review_count\":112,\"is_consumer_subscription_eligible\":false,\"offers_delivery\":true,\"max_order_size\":null,\"delivery_fee\":0,\"max_composite_score\":10,\"provides_external_courier_tracking\":false,\"id\":185337,\"average_rating\":4.7,\"tags\":[\"Seafood\",\"Hawaiian\",\"Asian Fusion\"],\"delivery_radius\":7607,\"inflation_rate\":0.0,\"menus\":[{\"status\":\"Pre-order for 11:30AM\",\"menu_version\":1,\"subtitle\":\"All Day\",\"name\":\"*Poki Bowl (All Day) (Palo Alto) (DD4B)\",\"open_hours\":[[{\"hour\":11,\"minute\":0},{\"hour\":19,\"minute\":30}],[{\"hour\":11,\"minute\":0},{\"hour\":19,\"minute\":30}],[{\"hour\":11,\"minute\":0},{\"hour\":19,\"minute\":30}],[{\"hour\":11,\"minute\":0},{\"hour\":19,\"minute\":30}],[{\"hour\":11,\"minute\":0},{\"hour\":19,\"minute\":30}],[{\"hour\":11,\"minute\":0},{\"hour\":19,\"minute\":30}],[{\"hour\":11,\"minute\":0},{\"hour\":19,\"minute\":30}]],\"is_business_enabled\":true,\"is_catering\":false,\"id\":302305,\"status_type\":\"pre-order\"},{\"status\":\"Pre-order for 11:30AM\",\"menu_version\":1,\"subtitle\":\"All Day\",\"name\":\"Poki Bowl (El Camino Real)\",\"open_hours\":[[{\"hour\":11,\"minute\":0},{\"hour\":19,\"minute\":30}],[{\"hour\":11,\"minute\":0},{\"hour\":19,\"minute\":30}],[{\"hour\":11,\"minute\":0},{\"hour\":19,\"minute\":30}],[{\"hour\":11,\"minute\":0},{\"hour\":19,\"minute\":30}],[{\"hour\":11,\"minute\":0},{\"hour\":19,\"minute\":30}],[{\"hour\":11,\"minute\":0},{\"hour\":19,\"minute\":30}],[{\"hour\":11,\"minute\":0},{\"hour\":19,\"minute\":30}]],\"is_business_enabled\":null,\"is_catering\":false,\"id\":283973,\"status_type\":\"pre-order\"}],\"show_store_menu_header_photo\":false,\"composite_score\":9,\"fulfills_own_deliveries\":false,\"offers_pickup\":true,\"number_of_ratings\":419,\"status_type\":\"pre-order\",\"is_only_catering\":false,\"status\":\"Pre-order for 11:30AM\",\"delivery_fee_details\":{\"final_fee\":{\"display_string\":\"$0.00\",\"unit_amount\":0},\"discount\":{\"description\":\"\",\"source_type\":\"first_delivery\",\"text\":\"Try Free Delivery\",\"discount_type\":\"try_free\",\"amount\":{\"display_string\":\"$3.99\",\"unit_amount\":399},\"min_subtotal\":{\"display_string\":\"$0.00\",\"unit_amount\":0}},\"surge_fee\":{\"display_string\":\"$0.00\",\"unit_amount\":0},\"original_fee\":{\"display_string\":\"$3.99\",\"unit_amount\":399}},\"object_type\":\"restaurant.restaurant\",\"description\":\"Seafood, Hawaiian, Asian Fusion\",\"business\":{\"business_vertical\":null,\"id\":22933,\"name\":\"Poki Bowl\"},\"yelp_biz_id\":\"poki-bowl-palo-alto\",\"asap_time\":null,\"should_show_store_logo\":true,\"yelp_rating\":4.0,\"extra_sos_delivery_fee\":0,\"business_id\":22933,\"special_instructions_max_length\":null,\"cover_img_url\":\"https://cdn.doordash.com/media/restaurant/cover/Poki-Bowl.png\",\"address\":{\"city\":\"Palo Alto\",\"subpremise\":\"b\",\"id\":11953901,\"printable_address\":\"Poki Bowl, 2305 El Camino Real b, Palo Alto, CA 94306, USA\",\"state\":\"CA\",\"street\":\"2305 El Camino Real\",\"country\":\"United States\",\"lat\":37.4257543,\"lng\":-122.1464657,\"shortname\":\"Poki Bowl\",\"zip_code\":\"94306\"},\"price_range\":2,\"slug\":\"poki-bowl-palo-alto\",\"show_suggested_items\":true,\"name\":\"Poki Bowl (El Camino Real)\",\"is_newly_added\":false,\"is_good_for_group_orders\":true,\"service_rate\":11.0,\"merchant_promotions\":[{\"minimum_subtotal_monetary_fields\":{\"currency\":\"USD\",\"display_string\":\"$0.00\",\"unit_amount\":null,\"decimal_places\":2},\"delivery_fee\":0,\"delivery_fee_monetary_fields\":{\"currency\":\"USD\",\"display_string\":\"$0.00\",\"unit_amount\":0,\"decimal_places\":2},\"minimum_subtotal\":null,\"new_store_customers_only\":true,\"id\":0}],\"header_image_url\":null}");

        server.enqueue(mockResponse);
        server.start();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(server.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        final RestaurantsApiService restaurantsApiService = retrofit.create(RestaurantsApiService.class);
        final Call<List<Results>> result = restaurantsApiService.getRestaurants("37.422740", "-122.139956",
                null, "1");


        result.enqueue(new Callback<List<Results>>() {
            @Override
            public void onResponse(Call<List<Results>> call, Response<List<Results>> response) {

                for (Results repository : response.body()) {
                    events.offer(repository.getName());
                    events.offer(repository.getImageUrl());
                }
            }

            @Override
            public void onFailure(Call<List<Results>> call, Throwable t) {

            }
        });

        assertThat(events.take(), is("Poki Bowl (El Camino Real)"));
        assertThat(events.take(), is("https://cdn.doordash.com/media/restaurant/cover/Poki-Bowl.png"));
        server.shutdown();
    }

}
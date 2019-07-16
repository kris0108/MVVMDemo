package com.mvvm.test;

import com.mvvm.test.model.QueryParameters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class TestQueryParams {

    private QueryParameters mQueryParameters;

    @Before
    public void setup() {
        mQueryParameters = new QueryParameters();
    }

    @Test
    public void testLatitudeParam() {
        String lat = mQueryParameters.getLatitude();
        assertEquals("37.422740", lat);
    }

    @Test
    public void testLongitudeParam() {
        String lng = mQueryParameters.getLongitude();
        assertEquals("-122.139956", lng);
    }
}

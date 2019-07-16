package com.mvvm.test;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mvvm.test.ui.RestaurantsLauncherActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class TestRestaurantListFragment {

    @Rule
    public final ActivityTestRule<RestaurantsLauncherActivity> activityTestRule =
            new ActivityTestRule<>(RestaurantsLauncherActivity.class);

    @Before
    public void init(){
        activityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void testAutoComplete(){
        onView(withId(R.id.loading_restaurants)).check(matches((isDisplayed())));
    }
}

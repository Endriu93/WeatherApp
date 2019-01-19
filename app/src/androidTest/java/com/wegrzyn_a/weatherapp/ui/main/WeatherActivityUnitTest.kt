package com.wegrzyn_a.weatherapp.ui.main

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.wegrzyn_a.weatherapp.R
import com.wegrzyn_a.weatherapp.runOnView
import junit.framework.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherActivityUnitTest{

    @get:Rule
    val activityRule = ActivityTestRule(WeatherActivity::class.java)

    @Test
    fun testShowTemp() {
        val temp = "1"

        runOnView(activityRule.activity as MVP.View) { it.showTempForToday(temp) }

        Espresso.onView(ViewMatchers.withId(R.id.today_temp))
            .check(ViewAssertions.matches(ViewMatchers.withText(temp)))
    }

    @Test
    fun testShowError() {
        val error = "error"

        runOnView(activityRule.activity as MVP.View) { it.showError(error) }

        onView(withText(error)).check(matches(isDisplayed()));
    }

    @Test
    fun testPresenterInjected() {
        assertNotNull((activityRule.activity as MVP.View).presenter)
    }
}
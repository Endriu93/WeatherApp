package com.wegrzyn_a.weatherapp.ui.main

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.wegrzyn_a.weatherapp.R
import com.wegrzyn_a.weatherapp.any
import com.wegrzyn_a.weatherapp.runOnView
import com.wegrzyn_a.weatherapp.ui.main.WeatherActivity.Companion.ICON_LOAD_SUCCESS
import junit.framework.Assert.assertNotNull
import org.hamcrest.CoreMatchers.`is`
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.declare
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


@RunWith(AndroidJUnit4::class)
class WeatherActivityUnitTest : KoinTest {

    @get:Rule
    val activityRule = object : ActivityTestRule<WeatherActivity>(WeatherActivity::class.java){
        override fun beforeActivityLaunched() {
            super.beforeActivityLaunched()
            MockitoAnnotations.initMocks(this@WeatherActivityUnitTest)
            declare { factory(override = true) { presenter } }
        }
    }

    @Mock
    lateinit var presenter: MVP.Presenter

    @Test
    fun testShowTemp() {
        val temp = "1"

        runOnView(activityRule.activity as MVP.View) { it.showTempForToday(temp) }

        Espresso.onView(ViewMatchers.withId(R.id.today_temp))
            .check(ViewAssertions.matches(ViewMatchers.withText(temp)))
    }

    @Test
    fun testPngIconLoadedAfter3s() {
        val iconUrl = "https://www.metaweather.com/static/img/weather/png/sn.png"

        runOnView(activityRule.activity as MVP.View) { it.showIconForToday(iconUrl) }

        Thread.sleep(3000)

        onView(withTagValue(`is`(ICON_LOAD_SUCCESS as Any))).check(matches(isDisplayed()));
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

    @Test
    fun testPresenterSubscribed() {
        verify((activityRule.activity as MVP.View).presenter).subscribe(any())
    }
}
package com.wegrzyn_a.weatherapp.ui.main

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.wegrzyn_a.weatherapp.R
import com.wegrzyn_a.weatherapp.any
import com.wegrzyn_a.weatherapp.mockWeatherAdapterItems
import com.wegrzyn_a.weatherapp.net.IconUrlFactory
import com.wegrzyn_a.weatherapp.runOnView
import junit.framework.Assert.assertNotNull
import org.hamcrest.CoreMatchers.`is`
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.standalone.get
import org.koin.test.KoinTest
import org.koin.test.declare
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


@RunWith(AndroidJUnit4::class)
class WeatherActivityUnitTest : KoinTest {

    @get:Rule
    val activityRule = object : ActivityTestRule<WeatherActivity>(WeatherActivity::class.java) {
        override fun beforeActivityLaunched() {
            super.beforeActivityLaunched()
            MockitoAnnotations.initMocks(this@WeatherActivityUnitTest)
            declare { factory(override = true) { presenter } }
        }
    }

    @Mock
    lateinit var presenter: MVP.Presenter

    @Test
    fun testShowTemperatureForNextDays() {
        val iconUrlFactory: IconUrlFactory = get()
        val adapterItems = mockWeatherAdapterItems(
            iconUrlFactory, listOf(
                "1.7" to "sn",
                "-3.79" to "lr",
                "-2.575" to "lr",
                "-1.105" to "lr",
                "-2.425" to "lr",
                "-5.46" to "lc"
            )
        )

        runOnView(activityRule.activity as MVP.View) { it.showWeathers(adapterItems) }

        adapterItems.forEach {
            onView(withId(R.id.recycler))
                .check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText(it.temp))))
        }
    }

    @Test
    fun testShowIconForNextDaysWith3sDelay() {
        val iconUrlFactory: IconUrlFactory = get()
        val adapterItems = mockWeatherAdapterItems(
            iconUrlFactory, listOf(
                "1.7" to "sn",
                "-3.79" to "lr",
                "-2.575" to "lr",
                "-1.105" to "lr",
                "-2.425" to "lr",
                "-5.46" to "lc"
            )
        )

        runOnView(activityRule.activity as MVP.View) { it.showWeathers(adapterItems) }
        Thread.sleep(3000)

        adapterItems.forEach {
            onView(withId(R.id.recycler))
                .check(ViewAssertions.matches(ViewMatchers.hasDescendant(withTagValue(`is`(it.iconUrl as Any)))))
        }
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
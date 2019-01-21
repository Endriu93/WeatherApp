package com.wegrzyn_a.weatherapp.ui.main

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withTagValue
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.GrantPermissionRule
import android.support.test.runner.AndroidJUnit4
import com.wegrzyn_a.weatherapp.R
import com.wegrzyn_a.weatherapp.data.model.Weather
import com.wegrzyn_a.weatherapp.mockAdapterItemsFromLocalFile
import com.wegrzyn_a.weatherapp.mockWeatherAdapterItems
import com.wegrzyn_a.weatherapp.mockWebServer
import com.wegrzyn_a.weatherapp.net.BaseUrlFactory
import com.wegrzyn_a.weatherapp.net.BaseUrlFactoryImpl
import com.wegrzyn_a.weatherapp.net.IconUrlFactory
import com.wegrzyn_a.weatherapp.scheduler.IoSchedulerFactory
import com.wegrzyn_a.weatherapp.scheduler.IoSchedulerFactoryImpl
import com.wegrzyn_a.weatherapp.sensor.LocationProvider
import com.wegrzyn_a.weatherapp.sensor.LocationProviderMockImpl
import com.wegrzyn_a.weatherapp.ui.main.adapter.WeatherAdapterItemBuilder
import io.reactivex.schedulers.TestScheduler
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.standalone.get
import org.koin.test.KoinTest
import org.koin.test.declare

@RunWith(AndroidJUnit4::class)
class WeatherActivityTest : KoinTest {

    lateinit var mockServer: MockWebServer

    lateinit var scheduler: TestScheduler

    @get:Rule
    val activityRule = ActivityTestRule<WeatherActivity>(WeatherActivity::class.java, true, false)

    @get:Rule
    val grantPermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(android.Manifest.permission.ACCESS_COARSE_LOCATION)

    @Before
    fun setUp() {
        mockServer = MockWebServer()
        mockServer.start()
        val url = mockServer.url("/v1/").toString()
        scheduler = TestScheduler()
        val woeid = 523920
        val latt = 50.0
        val long = 20.0
        mockWebServer(
            mockServer,
            pathToFileList = listOf(
                "/api/location/search/?lattlong=$latt,$long" to "api_location_search_50_19.json",
                "/api/location/$woeid" to "api_location_523920.json"
            )
        )
        declare {
            factory(override = true) {
                IoSchedulerFactoryImpl(
                    scheduler
                ) as IoSchedulerFactory
            }
        }
        declare { factory(override = true) { BaseUrlFactoryImpl(url) as BaseUrlFactory } }
        declare {
            factory(override = true) {
                LocationProviderMockImpl(
                    latt,
                    long
                ) as LocationProvider
            }
        }
    }

    @Test
    fun testShowTemperatureForNextDaysWith3sDelay() {
        activityRule.launchActivity(Intent())
        scheduler.triggerActions()
        val adapterItems = mockAdapterItemsFromLocalFile(this)

        Thread.sleep(5000)

        adapterItems.forEach {
            onView(withId(R.id.recycler))
                .check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText(it.temp))))
        }
    }

    @Test
    fun testShowIconForNextDaysWith3sDelay() {
        activityRule.launchActivity(Intent())
        scheduler.triggerActions()
        val adapterItems = mockAdapterItemsFromLocalFile(this)

        Thread.sleep(3000)

        adapterItems.forEach {
            onView(withId(R.id.recycler))
                .check(
                    ViewAssertions.matches(
                        ViewMatchers.hasDescendant(
                            withTagValue(
                                CoreMatchers.`is`(
                                    it.iconUrl as Any
                                )
                            )
                        )
                    )
                )
        }
    }
}
package com.wegrzyn_a.weatherapp.ui.main

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.GrantPermissionRule
import android.support.test.runner.AndroidJUnit4
import com.wegrzyn_a.weatherapp.R
import com.wegrzyn_a.weatherapp.data.DataSource
import com.wegrzyn_a.weatherapp.data.DataSourceImpl
import com.wegrzyn_a.weatherapp.data.model.LatLng
import com.wegrzyn_a.weatherapp.getJson
import com.wegrzyn_a.weatherapp.mockWebServer
import com.wegrzyn_a.weatherapp.sensor.LocationProvider
import com.wegrzyn_a.weatherapp.sensor.LocationProviderMockImpl
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.declare

@RunWith(AndroidJUnit4::class)
class WeatherActivityTest : KoinTest {

    lateinit var mockServer: MockWebServer

    lateinit var scheduler: TestScheduler

    @get:Rule
    val activityRule = ActivityTestRule<WeatherActivity>(WeatherActivity::class.java,true,false)

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
                InteractorImpl(
                    get(),
                    get(),
                    subscribeScheduler = scheduler,
                    observeScheduler = AndroidSchedulers.mainThread()
                ) as MVP.Interactor
            }
        }
        declare { factory(override = true) { DataSourceImpl(url) as DataSource } }
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
    fun TestTemperatureForTodayDisplayed() {
        activityRule.launchActivity(Intent())
        val temp = "1.7"

        scheduler.triggerActions()
        onView(withId(R.id.today_temp))
            .check(ViewAssertions.matches(withText(temp)))
    }
}
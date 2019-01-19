package com.wegrzyn_a.weatherapp

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@RunWith(AndroidJUnit4::class)
class WeatherActivityTest {

    var mockServer: MockWebServer? = null

    @get:Rule
    val activityRule = ActivityTestRule(WeatherActivity::class.java)

    @Before
    fun setUp() {
        mockServer = MockWebServer()
        mockServer?.start()
    }

    @Test
    fun TestTemperatureForTodayDisplayed() {
        mockServer?.setDispatcher(object : Dispatcher() {
            override fun dispatch(request: RecordedRequest?): MockResponse {
                if (request?.getPath().equals("/api/location/523920")) {
                    return MockResponse().setResponseCode(200).setBody(
                        getJson("api_location_523920.json")
                    )
                } else return MockResponse().setResponseCode(400)
            }
        })
        val temp = "1.7"
        onView(withId(R.id.today_temp))
            .check(ViewAssertions.matches(withText(temp)))
    }
}
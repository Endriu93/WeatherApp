package com.wegrzyn_a.weatherapp.ui.main

import com.wegrzyn_a.weatherapp.any
import com.wegrzyn_a.weatherapp.data.model.Weather
import com.wegrzyn_a.weatherapp.mockInteractorGetTemps
import com.wegrzyn_a.weatherapp.net.IconUrlFactory
import com.wegrzyn_a.weatherapp.ui.main.adapter.WeatherAdapterItem
import com.wegrzyn_a.weatherapp.ui.main.adapter.WeatherAdapterItemBuilder
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class PresenterImplTest {

    @Mock
    lateinit var view: MVP.View

    @Mock
    lateinit var interactor: MVP.Interactor

    @Mock
    lateinit var weatherAdapterItemBuilder: WeatherAdapterItemBuilder

    lateinit var presenter: PresenterImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = PresenterImpl(interactor,weatherAdapterItemBuilder)
    }

    @Test
    fun testSubscribe() {
        mockInteractorGetTemps(interactor,emptyList<Weather>())

        assertNull(presenter._view)
        presenter.subscribe(view)
        assertNotNull(presenter._view)
    }

    @Test
    fun testUnSubscribe() {
        presenter.unSubscribe()
        assertNull(presenter._view)
    }

    @Test
    fun testWeathersForNextDaysInvokedAfterSubscribe() {
        val temp = "1"
        val weather_state_abbr = "sn"
        val date = "2019-01-18"
        val generatedIconUrl = "generatedIconUrl"
        mockInteractorGetTemps(interactor,listOf(Weather(temp,weather_state_abbr,date)))
        val weatherAdapterItem = WeatherAdapterItem("","","")
        `when`(weatherAdapterItemBuilder.buildWeatherAdapterItem(any())).thenReturn(weatherAdapterItem)

        presenter.subscribe(view)

        verify(view).showWeathers(listOf(weatherAdapterItem))
    }

    @Test
    fun testInteractorReturnsEmptyList() {
        mockInteractorGetTemps(interactor,emptyList<Weather>())

        presenter.subscribe(view)

        verify(view).showError(any())
    }

}
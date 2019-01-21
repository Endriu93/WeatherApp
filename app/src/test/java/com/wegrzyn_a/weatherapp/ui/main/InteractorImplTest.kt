package com.wegrzyn_a.weatherapp.ui.main

import com.wegrzyn_a.weatherapp.data.DataSource
import com.wegrzyn_a.weatherapp.data.model.Measurement
import com.wegrzyn_a.weatherapp.data.model.Station
import com.wegrzyn_a.weatherapp.data.model.Weather
import com.wegrzyn_a.weatherapp.mockLocation
import com.wegrzyn_a.weatherapp.mockDataSourceMeasurement
import com.wegrzyn_a.weatherapp.mockDataSourceStations
import com.wegrzyn_a.weatherapp.sensor.LocationProvider
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class InteractorImplTest {

    lateinit var interactor: InteractorImpl

    lateinit var scheduler: TestScheduler

    @Mock
    lateinit var dataSource: DataSource

    @Mock
    lateinit var locationProvider: LocationProvider

    @Mock
    lateinit var onSuccess: (List<String>) -> Unit

    @Mock
    lateinit var onError: (String) -> Unit

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        scheduler = TestScheduler()
        interactor = InteractorImpl(
            dataSource,
            locationProvider,
            subscribeScheduler = scheduler,
            observeScheduler = scheduler
        )
    }

    @Test
    fun testSuccessfulGetTemps() {
        val latt = 50.06143
        val long = 19.93658
        val woeid = 523920
        val temp = "3"
        mockLocation(locationProvider,latt,long)
        mockDataSourceStations(dataSource,latt, long, listOf(Station("...",woeid)))
        mockDataSourceMeasurement(dataSource,woeid, Measurement(listOf(Weather(temp)),woeid))

        interactor.getTempsForNextDays().subscribe({onSuccess(it)},{})
        scheduler.triggerActions()

        verify(onSuccess).invoke(listOf(temp))
    }

}
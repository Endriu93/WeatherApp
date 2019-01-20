package com.wegrzyn_a.weatherapp.ui.main

import com.wegrzyn_a.weatherapp.data.DataSource
import com.wegrzyn_a.weatherapp.data.model.Measurement
import com.wegrzyn_a.weatherapp.data.model.Station
import com.wegrzyn_a.weatherapp.data.model.Weather
import com.wegrzyn_a.weatherapp.sensor.LocationProvider
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
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
        `when`(locationProvider.getLattLong()).thenReturn(Pair(latt, long))
        `when`(dataSource.getStations(latt, long)).thenReturn(
            Single.just(
                listOf(
                    Station(
                        "...",
                        woeid
                    )
                )
            )
        )
        `when`(dataSource.getMeasurements(woeid)).thenReturn(
            Single.just(
                Measurement(
                    listOf(
                        Weather(
                            temp
                        )
                    )
                )
            )
        )

        interactor.getTemps(onSuccess)
        scheduler.triggerActions()

        verify(onSuccess).invoke(listOf(temp))
    }
}
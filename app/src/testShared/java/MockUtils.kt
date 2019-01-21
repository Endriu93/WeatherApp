package com.wegrzyn_a.weatherapp

import com.wegrzyn_a.weatherapp.data.DataSource
import com.wegrzyn_a.weatherapp.data.model.LatLng
import com.wegrzyn_a.weatherapp.data.model.Measurement
import com.wegrzyn_a.weatherapp.data.model.Station
import com.wegrzyn_a.weatherapp.data.model.Weather
import com.wegrzyn_a.weatherapp.sensor.LocationProvider
import com.wegrzyn_a.weatherapp.ui.main.MVP
import com.wegrzyn_a.weatherapp.ui.main.adapter.WeatherAdapterItem
import com.wegrzyn_a.weatherapp.ui.main.adapter.WeatherAdapterItemBuilder
import io.reactivex.Single
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.koin.standalone.KoinComponent
import org.koin.standalone.get
import org.mockito.Mockito
import org.mockito.Mockito.`when`


fun mockLocation(locationProvider: LocationProvider, latt: Double, long: Double) {
    `when`(locationProvider.getLatLng()).thenReturn(Single.just(LatLng(latt, long)))
}

fun mockDataSourceStations(
    dataSource: DataSource,
    latt: Double,
    long: Double,
    stations: List<Station>
) {
    Mockito.`when`(dataSource.getStations(LatLng(latt, long))).thenReturn(
        Single.just(
            stations
        )
    )
}

fun mockDataSourceMeasurement(dataSource: DataSource, woeid: Int, measurement: Measurement) {
    Mockito.`when`(dataSource.getMeasurements(woeid)).thenReturn(
        Single.just(
            measurement
        )
    )
}

fun mockInteractorGetTemps(interactor: MVP.Interactor, weathers: List<Weather>) {
    `when`(interactor.getTempsForNextDays()).thenReturn(Single.just(weathers))
}

fun mockWeatherAdapterItems(
    weatherAdapterItemBuilder: WeatherAdapterItemBuilder,
    items: List<Weather>
): List<WeatherAdapterItem> {
    return items.map { weatherAdapterItemBuilder.buildWeatherAdapterItem(it) }
}

fun mockAdapterItemsFromLocalFile(koinTest: KoinComponent): List<WeatherAdapterItem> {
    val weatherAdapterItemBuilder: WeatherAdapterItemBuilder = koinTest.get()
    val adapterItems = mockWeatherAdapterItems(
        weatherAdapterItemBuilder, listOf(
            Weather("1.7", "sn", "2019-01-18"),
            //                Weather("-3.79", "lr", "2019-01-19"),
            Weather("-2.575", "lr", "2019-01-20"),
            Weather("-1.105", "lr", "2019-01-21"),
            Weather("-2.425", "lr", "2019-01-22"),
            Weather("-5.46", "lc", "2019-01-23")
        )
    )
    return adapterItems
}

fun mockWebServer(mockServer: MockWebServer, pathToFileList: List<Pair<String, String>>) {
    mockServer.setDispatcher(object : Dispatcher() {
        override fun dispatch(request: RecordedRequest?): MockResponse {
            val matchingPath =
                pathToFileList.filter { request?.getPath()?.replace("%2C", ",").equals(it.first) }
                    .firstOrNull()

            if (matchingPath != null)
                return MockResponse().setResponseCode(200).setBody(
                    getJson(matchingPath.second)
                )
            else
                return MockResponse().setResponseCode(400)
        }
    })
}



package com.wegrzyn_a.weatherapp.data

import com.wegrzyn_a.weatherapp.data.model.LatLng
import com.wegrzyn_a.weatherapp.data.model.Measurement
import com.wegrzyn_a.weatherapp.data.model.Station
import com.wegrzyn_a.weatherapp.mockWebServer
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class DataSourceImplTest {

    lateinit var mockServer: MockWebServer
    lateinit var dataSourceImpl: DataSourceImpl

    @Before
    fun setUp() {
        mockServer = MockWebServer()
        mockServer.start()
        dataSourceImpl = DataSourceImpl(mockServer.url("v1/").toString())
    }

    @Test
    fun testGetStations() {
        val latt = 50.06143;
        val long = 19.93658
        mockWebServer(
            mockServer,
            listOf("/api/location/search/?lattlong=$latt,$long" to "api_location_search_50_19.json")
        )

        var result: List<Station>? = null
        dataSourceImpl.getStations(LatLng(latt,long)).subscribe({result = it; println(it)},{println(it)})

        assertNotNull(result)
        assertEquals(10,result?.size)
        assertEquals("Warsaw",result?.get(0)?.title)
    }

    @Test
    fun testGetMeasurement() {
        val woeid = 523920
        mockWebServer(
            mockServer,
            listOf("/api/location/$woeid" to "api_location_523920.json")
        )

        var result: Measurement? = null
        dataSourceImpl.getMeasurements(woeid).subscribe({result = it},{})

        assertNotNull(result)
        assertEquals(woeid,result?.woeid)
    }
}
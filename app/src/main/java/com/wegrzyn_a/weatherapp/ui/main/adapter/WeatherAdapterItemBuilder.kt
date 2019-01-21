package com.wegrzyn_a.weatherapp.ui.main.adapter

import com.wegrzyn_a.weatherapp.data.model.Weather

interface WeatherAdapterItemBuilder {
    fun buildWeatherAdapterItem(weather: Weather): WeatherAdapterItem
}
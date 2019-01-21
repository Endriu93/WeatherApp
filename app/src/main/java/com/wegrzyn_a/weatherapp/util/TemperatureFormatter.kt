package com.wegrzyn_a.weatherapp.util

interface TemperatureFormatter {
    fun getReadableTemp(temp: String): String
}
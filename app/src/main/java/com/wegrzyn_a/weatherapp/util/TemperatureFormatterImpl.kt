package com.wegrzyn_a.weatherapp.util

import kotlin.math.round

class TemperatureFormatterImpl : TemperatureFormatter {
    override fun getReadableTemp(temp: String): String {
        return round(temp.toDouble()).toInt().toString()+ 0x00B0.toChar()
    }
}
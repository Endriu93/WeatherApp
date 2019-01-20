package com.wegrzyn_a.weatherapp.sensor

interface LocationProvider {
    fun getLattLong(): Pair<Double,Double>
}
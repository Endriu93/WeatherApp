package com.wegrzyn_a.weatherapp.data.model

data class LatLng(val latt: Double, val long: Double) {
    override fun toString(): String {
        return "$latt,$long"
    }
}
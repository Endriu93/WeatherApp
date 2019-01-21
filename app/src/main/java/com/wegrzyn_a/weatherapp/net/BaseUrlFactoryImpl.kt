package com.wegrzyn_a.weatherapp.net

class BaseUrlFactoryImpl(val url: String) : BaseUrlFactory {
    override fun getBaseUrl() = url
}
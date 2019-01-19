package com.wegrzyn_a.weatherapp

import android.app.Application
import com.wegrzyn_a.weatherapp.ui.main.di.weatherModule
import org.koin.android.ext.android.startKoin

class WeatherApplication : Application() {
    override fun onCreate(){
        super.onCreate()
        // start Koin!
        startKoin(this, listOf(weatherModule))
    }
}

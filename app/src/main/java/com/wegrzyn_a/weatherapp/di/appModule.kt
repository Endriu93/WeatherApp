package com.wegrzyn_a.weatherapp.di

import com.wegrzyn_a.weatherapp.data.DataSource
import com.wegrzyn_a.weatherapp.data.DataSourceImpl
import com.wegrzyn_a.weatherapp.net.BaseUrlFactory
import com.wegrzyn_a.weatherapp.net.BaseUrlFactoryImpl
import com.wegrzyn_a.weatherapp.net.IconUrlFactory
import com.wegrzyn_a.weatherapp.net.IconUrlFactoryImpl
import com.wegrzyn_a.weatherapp.scheduler.AndroidSchedulerFactory
import com.wegrzyn_a.weatherapp.scheduler.AndroidSchedulerFactoryImpl
import com.wegrzyn_a.weatherapp.scheduler.IoSchedulerFactory
import com.wegrzyn_a.weatherapp.scheduler.IoSchedulerFactoryImpl
import com.wegrzyn_a.weatherapp.sensor.LocationProvider
import com.wegrzyn_a.weatherapp.sensor.LocationProviderImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val appModule = module {
    factory { DataSourceImpl((get() as BaseUrlFactory).getBaseUrl()) as DataSource }
    factory { LocationProviderImpl(androidContext()) as LocationProvider }
    factory { BaseUrlFactoryImpl("https://www.metaweather.com") as BaseUrlFactory }
    factory { IconUrlFactoryImpl { icon_abbr -> "https://www.metaweather.com/static/img/weather/png/$icon_abbr.png" } as IconUrlFactory }
    factory { IoSchedulerFactoryImpl(Schedulers.io()) as IoSchedulerFactory }
    factory { AndroidSchedulerFactoryImpl(AndroidSchedulers.mainThread()) as AndroidSchedulerFactory }
}
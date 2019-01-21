package com.wegrzyn_a.weatherapp.ui.main.di

import com.wegrzyn_a.weatherapp.data.DataSource
import com.wegrzyn_a.weatherapp.data.DataSourceImpl
import com.wegrzyn_a.weatherapp.sensor.LocationProvider
import com.wegrzyn_a.weatherapp.sensor.LocationProviderImpl
import com.wegrzyn_a.weatherapp.ui.main.InteractorImpl
import com.wegrzyn_a.weatherapp.ui.main.MVP
import com.wegrzyn_a.weatherapp.ui.main.PresenterImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module
import org.mockito.Mockito.mock

val weatherModule = module {
    factory { PresenterImpl(get()) as MVP.Presenter }
    factory {
        InteractorImpl(
            get(),
            get(),
            subscribeScheduler = Schedulers.io(),
            observeScheduler = AndroidSchedulers.mainThread()
        ) as MVP.Interactor
    }
    factory { DataSourceImpl("https://www.metaweather.com") as DataSource }
    factory { LocationProviderImpl(androidContext()) as LocationProvider}
}
package com.wegrzyn_a.weatherapp.ui.main.di

import com.wegrzyn_a.weatherapp.scheduler.AndroidSchedulerFactory
import com.wegrzyn_a.weatherapp.scheduler.IoSchedulerFactory
import com.wegrzyn_a.weatherapp.ui.main.InteractorImpl
import com.wegrzyn_a.weatherapp.ui.main.MVP
import com.wegrzyn_a.weatherapp.ui.main.PresenterImpl
import org.koin.dsl.module.module

val weatherModule = module {
    factory { PresenterImpl(get(),get()) as MVP.Presenter }
    factory {
        InteractorImpl(
            get(),
            get(),
            subscribeScheduler = (get() as IoSchedulerFactory).getIOScheduler(),
            observeScheduler = (get() as AndroidSchedulerFactory).getAndroidScheduler()
        ) as MVP.Interactor
    }
}
package com.wegrzyn_a.weatherapp.ui.main.di

import com.wegrzyn_a.weatherapp.ui.main.InteractorImpl
import com.wegrzyn_a.weatherapp.ui.main.MVP
import com.wegrzyn_a.weatherapp.ui.main.PresenterImpl
import org.koin.dsl.module.module

val weatherModule = module {
    factory { PresenterImpl(get()) as MVP.Presenter }
    factory { InteractorImpl() as MVP.Interactor }
}
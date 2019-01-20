package com.wegrzyn_a.weatherapp.ui.main.di

import com.wegrzyn_a.weatherapp.ui.main.MVP
import com.wegrzyn_a.weatherapp.ui.main.PresenterImpl
import org.koin.dsl.module.module
import org.mockito.Mockito.mock

val weatherModule = module {
    factory { PresenterImpl(get()) as MVP.Presenter }
    factory { mock(MVP.Interactor::class.java) }
}
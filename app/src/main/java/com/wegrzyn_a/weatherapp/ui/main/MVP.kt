package com.wegrzyn_a.weatherapp.ui.main

import com.wegrzyn_a.weatherapp.data.model.Weather
import com.wegrzyn_a.weatherapp.ui.main.adapter.WeatherAdapterItem
import io.reactivex.Single

interface MVP {
    interface View {
        val presenter: Presenter
        fun showError(error: String)
        fun showWeathers(items: List<WeatherAdapterItem>)
    }

    interface Presenter {
        fun subscribe(view: View)
        fun unSubscribe()
    }

    interface Interactor {
        fun getTempsForNextDays(): Single<List<Weather>>
        fun unSubscribe()
    }
}
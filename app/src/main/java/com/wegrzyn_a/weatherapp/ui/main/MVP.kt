package com.wegrzyn_a.weatherapp.ui.main

import com.wegrzyn_a.weatherapp.data.model.Weather
import io.reactivex.Single

interface MVP {
    interface View {
        val presenter: Presenter
        fun showTempForToday(temp: String)
        fun showIconForToday(iconUrl: String)
        fun showError(error: String)
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
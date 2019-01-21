package com.wegrzyn_a.weatherapp.ui.main

import com.wegrzyn_a.weatherapp.net.IconUrlFactory
import com.wegrzyn_a.weatherapp.ui.main.adapter.WeatherAdapterItem
import io.reactivex.disposables.CompositeDisposable


class PresenterImpl(val interactor: MVP.Interactor, val iconUrlFactory: IconUrlFactory) :
    MVP.Presenter {

    var _view: MVP.View? = null
    var subscriptions = CompositeDisposable()

    override fun subscribe(view: MVP.View) {
        this._view = view

        val subscription = interactor.getTempsForNextDays().subscribe(
            { weather_list ->
                if (weather_list.size > 0) {
                    this._view?.showWeathers(weather_list.map { weather ->
                        WeatherAdapterItem(
                            weather.the_temp,
                            iconUrlFactory.getIconUrl(weather.weather_state_abbr)
                        )
                    })
                } else
                    this._view?.showError("empty weather list")
            },
            {
                this._view?.showError(it.message ?: "internal error")
            })
        subscriptions.add(subscription)
    }

    override fun unSubscribe() {
        this._view = null
        subscriptions.clear()
    }
}
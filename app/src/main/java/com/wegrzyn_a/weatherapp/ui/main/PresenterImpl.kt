package com.wegrzyn_a.weatherapp.ui.main

import com.wegrzyn_a.weatherapp.net.IconUrlFactory
import io.reactivex.disposables.CompositeDisposable


class PresenterImpl(val interactor: MVP.Interactor, val iconUrlFactory: IconUrlFactory) :
    MVP.Presenter {

    var _view: MVP.View? = null
    var subscriptions = CompositeDisposable()

    override fun subscribe(view: MVP.View) {
        this._view = view

        val subscription = interactor.getTempsForNextDays().subscribe(
            {
                if (it.size > 0) {
                    val weather_today = it.get(0)
                    this._view?.showTempForToday(weather_today.the_temp)
                    this._view?.showIconForToday(iconUrlFactory.getIconUrl(weather_today.weather_state_abbr))
                } else
                    this._view?.showError("empty temperature list")
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
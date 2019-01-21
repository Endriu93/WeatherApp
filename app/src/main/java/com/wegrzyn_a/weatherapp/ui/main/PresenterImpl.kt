package com.wegrzyn_a.weatherapp.ui.main

import android.util.Log
import com.wegrzyn_a.weatherapp.sensor.LocationNotObtainedException
import com.wegrzyn_a.weatherapp.ui.main.adapter.WeatherAdapterItemBuilder
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.exceptions.CompositeException


class PresenterImpl(
    val interactor: MVP.Interactor,
    val weatherAdapterItemBuilder: WeatherAdapterItemBuilder
) :
    MVP.Presenter {

    companion object {
        const val LOG_TAG = "PresenterImpl"
    }

    var _view: MVP.View? = null
    var subscriptions = CompositeDisposable()

    override fun subscribe(view: MVP.View) {
        this._view = view

        val subscription = interactor.getTempsForNextDays().subscribe(
            { weather_list ->
                if (weather_list.size > 0) {
                    this._view?.showWeathers(weather_list.map { weather -> weatherAdapterItemBuilder.buildWeatherAdapterItem(weather) })
                } else
                    this._view?.showError("empty weather list")
            },
            {
                val exception = if (it is CompositeException) it.exceptions.last() else it
                val error = when (exception) {
                    is LocationNotObtainedException -> "Couldn't get current location!"
                    else -> "internal error"
                }
                this._view?.showError(error);
                Log.d(LOG_TAG, error, it)
            })
        subscriptions.add(subscription)
    }

    override fun unSubscribe() {
        this._view = null
        subscriptions.clear()
    }
}
package com.wegrzyn_a.weatherapp.ui.main

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.subscriptions.ArrayCompositeSubscription
import org.reactivestreams.Subscription


class PresenterImpl(val interactor: MVP.Interactor) : MVP.Presenter {

    var _view: MVP.View? = null
    var subscriptions = CompositeDisposable()

    override fun subscribe(view: MVP.View) {
        this._view = view

        val subscription = interactor.getTempsForNextDays().subscribe(
                {
                    if (it.size > 0)
                        this._view?.showTempForToday(it.get(0))
                    else
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
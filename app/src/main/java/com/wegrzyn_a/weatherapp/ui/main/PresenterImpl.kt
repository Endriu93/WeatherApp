package com.wegrzyn_a.weatherapp.ui.main

class PresenterImpl(val interactor: MVP.Interactor) : MVP.Presenter {

    var view: MVP.View? = null

    override fun subscribe(_view: MVP.View) {
        this.view = _view

        interactor.getTemps(
            onSuccess = {
                if (it.size > 0)
                    view?.showTempForToday(it.get(0))
                else view?.showError("Couldn't load temperatures")
            },
            onError = { view?.showError(it) })
    }

    override fun unSubscribe() {
        this.view = null
    }
}
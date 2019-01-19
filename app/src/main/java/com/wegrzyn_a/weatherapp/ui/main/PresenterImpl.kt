package com.wegrzyn_a.weatherapp.ui.main

class PresenterImpl(val interactor: MVP.Interactor) : MVP.Presenter {

    var view: MVP.View? = null

    override fun subscribe(view: MVP.View) {
        this.view = view

        interactor.getTemps {
            if (it.size > 0) view.showTempForToday(it.get(0)) else view.showError(
                "Couldn't load temperatures"
            )
        }
    }

    override fun unSubscribe() {
        this.view = null
    }
}
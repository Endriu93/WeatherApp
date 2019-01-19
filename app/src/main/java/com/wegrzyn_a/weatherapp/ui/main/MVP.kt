package com.wegrzyn_a.weatherapp.ui.main

interface MVP {
    interface View {
        fun showTempForToday(temp: String)
    }

    interface Presenter {
        fun subscribe(view: View)
        fun unSubscribe()
    }

    interface Interactor {
        fun getTemps(): List<String>
        fun unSubscribe()
    }
}
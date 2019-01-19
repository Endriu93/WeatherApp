package com.wegrzyn_a.weatherapp.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.wegrzyn_a.weatherapp.R
import kotlinx.android.synthetic.main.activity_weather.*

class WeatherActivity : AppCompatActivity(), MVP.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
    }

    override fun showTempForToday(temp: String) {
        today_temp.text = temp
    }

    override fun showError(error: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(error)
        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            dialog.cancel()
        }
        builder.show()
    }
}

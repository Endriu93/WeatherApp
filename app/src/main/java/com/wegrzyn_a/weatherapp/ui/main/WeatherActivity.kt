package com.wegrzyn_a.weatherapp.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import com.wegrzyn_a.weatherapp.R
import kotlinx.android.synthetic.main.activity_weather.*
import org.koin.android.ext.android.inject

class WeatherActivity : AppCompatActivity(), MVP.View {

    companion object {
        const val REQUEST_FINE_LOCATION = 1
    }

    override val presenter: MVP.Presenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
    }

    override fun onStart() {
        super.onStart()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_FINE_LOCATION
            )
        } else
            presenter.subscribe(this)
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_FINE_LOCATION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED))
                    presenter.subscribe(this)
            }
        }
    }
}

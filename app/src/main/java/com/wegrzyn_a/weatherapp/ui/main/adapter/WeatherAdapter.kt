package com.wegrzyn_a.weatherapp.ui.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.wegrzyn_a.weatherapp.R
import kotlinx.android.synthetic.main.weather_item.view.*

class WeatherAdapter(var items: List<WeatherAdapterItem>) :
    RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                p0.context
            ).inflate(R.layout.weather_item, p0, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(viewHolder: ViewHolder, index: Int) {
        viewHolder.date.text = items.get(index).date
        viewHolder.temp.text = items.get(index).temp
        val iconUrl = items.get(index).iconUrl
        Picasso.get().load(iconUrl).into(viewHolder.icon, object : Callback {
            override fun onSuccess() {
                viewHolder.icon.tag = iconUrl
            }

            override fun onError(e: Exception?) {
                viewHolder.icon.tag = ""
            }
        })
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date = view.item_date
        val temp = view.item_temp
        val icon = view.item_icon
    }

    fun loadWeathers(notes: List<WeatherAdapterItem>) {
        this.items = notes
        notifyDataSetChanged()
    }
}
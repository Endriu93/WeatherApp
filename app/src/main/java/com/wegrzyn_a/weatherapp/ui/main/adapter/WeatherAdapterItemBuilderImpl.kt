package com.wegrzyn_a.weatherapp.ui.main.adapter

import com.wegrzyn_a.weatherapp.data.model.Weather
import com.wegrzyn_a.weatherapp.net.IconUrlFactory
import com.wegrzyn_a.weatherapp.util.DateFormatter
import com.wegrzyn_a.weatherapp.util.TemperatureFormatter

class WeatherAdapterItemBuilderImpl(val iconUrlFactory: IconUrlFactory, val dateFormatter: DateFormatter, val temperatureFormatter: TemperatureFormatter) : WeatherAdapterItemBuilder {
    override fun buildWeatherAdapterItem(weather: Weather): WeatherAdapterItem {
        return WeatherAdapterItem(
            temp = temperatureFormatter.getReadableTemp(weather.the_temp),
            iconUrl = iconUrlFactory.getIconUrl(weather.weather_state_abbr),
            date = dateFormatter.getReadableDate(weather.applicable_date)
        )
    }
}
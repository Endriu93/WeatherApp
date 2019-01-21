package com.wegrzyn_a.weatherapp.net

interface IconUrlFactory {
    fun getIconUrl(icon_abbr: String): String
}
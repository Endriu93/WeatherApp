package com.wegrzyn_a.weatherapp.net

class IconUrlFactoryImpl(val urlCreator: (String)->String) : IconUrlFactory {
    override fun getIconUrl(icon_abbr: String) = urlCreator.invoke(icon_abbr)
}
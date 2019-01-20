package com.wegrzyn_a.weatherapp.data.model

data class Measurement(val consolidated_weather: List<Weather>, val woeid: Int)

/*
"consolidated_weather": [
{
    "id": 5146843822424064,
    "weather_state_name": "Snow",
    "weather_state_abbr": "sn",
    "wind_direction_compass": "WNW",
    "created": "2019-01-18T11:51:15.309362Z",
    "applicable_date": "2019-01-18",
    "min_temp": -0.94,
    "max_temp": 3.0749999999999997,
    "the_temp": 1.7,
    "wind_speed": 9.323026197629842,
    "wind_direction": 281.50324245311384,
    "air_pressure": 1007.825,
    "humidity": 84,
    "visibility": 14.955161854768154,
    "predictability": 90
}
],
"time": "2019-01-18T14:00:24.672259+01:00",
"sun_rise": "2019-01-18T07:35:38.188946+01:00",
"sun_set": "2019-01-18T15:57:22.705795+01:00",
"timezone_name": "LMT",
"parent": {
    "title": "Poland",
    "location_type": "Country",
    "woeid": 23424923,
    "latt_long": "51.918919,19.134300"
},
"sources": [
{
    "title": "BBC",
    "slug": "bbc",
    "url": "http://www.bbc.co.uk/weather/",
    "crawl_rate": 180
}
],
"title": "Warsaw",
"location_type": "City",
"woeid": 523920,
"latt_long": "52.235352,21.009390",
"timezone": "Europe/Warsaw"*/

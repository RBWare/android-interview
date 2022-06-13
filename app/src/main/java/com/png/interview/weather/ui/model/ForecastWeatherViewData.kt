package com.png.interview.weather.ui.model

data class ForecastWeatherViewData(
    val date: String,
    val minTemperature: String,
    val maxTemperature: String,
    val condition: String,
    val windSpeed: String
)
package com.png.interview.weather.ui.model

sealed class ForecastViewRepresentation {
    class ForecastWeatherViewRep(val data: List<ForecastWeatherViewData>) : ForecastViewRepresentation()

    object Empty : ForecastViewRepresentation()
    object Error : ForecastViewRepresentation()
}

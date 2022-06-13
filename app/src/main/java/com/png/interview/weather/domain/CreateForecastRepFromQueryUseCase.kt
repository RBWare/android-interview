package com.png.interview.weather.domain

import com.png.interview.api.common_model.NetworkResponse
import com.png.interview.weather.api.model.Forecastday
import com.png.interview.weather.ui.model.ForecastViewRepresentation
import com.png.interview.weather.ui.model.ForecastWeatherViewData
import javax.inject.Inject

interface CreateForecastRepFromQueryUseCase {
    suspend operator fun invoke(query: String, isMetric: Boolean): ForecastViewRepresentation
}

class DefaultCreateForecastRepFromQueryUseCase @Inject constructor(
    private val getForecastDataUseCase: GetForecastDataUseCase
) : CreateForecastRepFromQueryUseCase {
    override suspend fun invoke(query: String, isMetric: Boolean): ForecastViewRepresentation {
        return when (val result = getForecastDataUseCase(query)) {
            is NetworkResponse.Success -> {
                val forecastDays: MutableList<ForecastWeatherViewData> = mutableListOf()
                result.body.forecast.forecastday.forEach {
                    forecastDays.add(ForecastWeatherViewData(
                        date = it.date,
                        minTemperature = when(isMetric) {
                            true -> "${it.day.mintemp_c} C"
                            false -> "${it.day.mintemp_f} F"
                        },
                        maxTemperature = when(isMetric) {
                            true -> "${it.day.maxtemp_c} C"
                            false -> "${it.day.maxtemp_f} F"
                        },
                        windSpeed =  when(isMetric) {
                            true -> "${it.day.maxwind_kph} KPH"
                            false -> "${it.day.maxwind_mph} MPH"
                        },
                        condition = it.day.condition.text
                    ))
                }
                ForecastViewRepresentation.ForecastWeatherViewRep(forecastDays)
            }
            else -> {
                ForecastViewRepresentation.Error
            }
        }
    }
}
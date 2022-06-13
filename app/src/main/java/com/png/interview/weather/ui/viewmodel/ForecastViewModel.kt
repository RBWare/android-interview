package com.png.interview.weather.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.png.interview.weather.domain.CreateForecastRepFromQueryUseCase
import com.png.interview.weather.ui.model.ForecastViewRepresentation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class ForecastViewModel @Inject constructor(
    private val createForecastRepFromQueryUseCase: CreateForecastRepFromQueryUseCase
) : ViewModel() {

    private val _forecastViewRepresentation = MutableStateFlow<ForecastViewRepresentation>(ForecastViewRepresentation.Empty)

    fun submitDailyForecastSearch(query: String, isMetric: Boolean) {
        viewModelScope.launch {
            _forecastViewRepresentation.value = createForecastRepFromQueryUseCase(query, isMetric)
        }
    }

    val availableForecastLiveData =
        _forecastViewRepresentation
            .map { (it as? ForecastViewRepresentation.ForecastWeatherViewRep) }
            .asLiveData()

    val isEmptyVisible =
        _forecastViewRepresentation
            .map { it is ForecastViewRepresentation.Empty }
            .asLiveData()

    val isErrorVisible =
        _forecastViewRepresentation
            .map { it is ForecastViewRepresentation.Error }
            .asLiveData()
}
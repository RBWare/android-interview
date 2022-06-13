package com.png.interview.weather.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.png.interview.weather.domain.CreateAutoCompleteRepFromQueryUseCase
import com.png.interview.weather.domain.CreateCurrentWeatherRepFromQueryUseCase
import com.png.interview.weather.domain.CreateForecastRepFromQueryUseCase
import com.png.interview.weather.ui.model.AutoCompleteViewRepresentation
import com.png.interview.weather.ui.model.CurrentWeatherViewRepresentation
import com.png.interview.weather.ui.model.ForecastViewRepresentation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class CurrentWeatherViewModel @Inject constructor(
    private val createCurrentWeatherRepFromQueryUseCase: CreateCurrentWeatherRepFromQueryUseCase,
    private val createAutoCompleteRepFromQueryUseCase: CreateAutoCompleteRepFromQueryUseCase
) : ViewModel() {

    private val _currentWeatherViewRepresentation = MutableStateFlow<CurrentWeatherViewRepresentation>(CurrentWeatherViewRepresentation.Empty)
    private val _autocompleteViewRepresentation = MutableStateFlow<AutoCompleteViewRepresentation>(AutoCompleteViewRepresentation.Empty)

    var mostRecentSearch: String = ""

    fun submitCurrentWeatherSearch(query: String, isMetric: Boolean) {
        mostRecentSearch = query
        viewModelScope.launch {
            _currentWeatherViewRepresentation.value = createCurrentWeatherRepFromQueryUseCase(query, isMetric)
        }
    }

    fun autoCompleteSearch(query: String) {
        viewModelScope.launch {
            _autocompleteViewRepresentation.value = createAutoCompleteRepFromQueryUseCase(query)
        }
    }

    val availableCurrentWeatherLiveData =
        _currentWeatherViewRepresentation
            .map { (it as? CurrentWeatherViewRepresentation.AvailableWeatherViewRep)?.data }
            .asLiveData()

    val availableAutoCompleteLiveData =
        _autocompleteViewRepresentation
            .map { (it as? AutoCompleteViewRepresentation.AutoCompleteViewRep)?.data }
            .asLiveData()

    val isEmptyVisible =
        _currentWeatherViewRepresentation
            .map { it is CurrentWeatherViewRepresentation.Empty }
            .asLiveData()

    val isErrorVisible =
        _currentWeatherViewRepresentation
            .map { it is CurrentWeatherViewRepresentation.Error }
            .asLiveData()
}
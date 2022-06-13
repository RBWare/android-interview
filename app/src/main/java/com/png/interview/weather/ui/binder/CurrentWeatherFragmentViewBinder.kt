package com.png.interview.weather.ui.binder

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.png.interview.weather.ui.model.AutoCompleteViewData
import com.png.interview.weather.ui.viewmodel.CurrentWeatherViewModel

/**
 * {@link ViewBinder}
 */
class CurrentWeatherFragmentViewBinder(
    private val viewModel: CurrentWeatherViewModel,
    private val activity: Activity,
    private val settingsAction: () -> Unit,
    private val forecastAction: (String, Boolean) -> Unit
) {

    val availableWeatherViewData = viewModel.availableCurrentWeatherLiveData
    val availableAutoCompleteData = viewModel.availableAutoCompleteLiveData
    val isEmpty = viewModel.isEmptyVisible
    val isError = viewModel.isErrorVisible

    var input: String = ""

    /**
     * Handles the "Refresh" button action
     */
    fun refreshClicked() {
        availableWeatherViewData.value?.let { viewModel.submitCurrentWeatherSearch(it.name, getMetric()) }
    }

    /**
     * Handles the "See Forecast" button action
     */
    fun seeForecastClicked() {
        forecastAction(viewModel.mostRecentSearch, getMetric())
    }

    /**
     * Handles the "Settings" button action
     */
    fun settingsClicked() {
        settingsAction()
    }

    /**
     * Handles the "Go" button action
     */
    fun goClicked() {
        if (input.isEmpty()) {
            Toast.makeText(activity, "Please Enter Query", Toast.LENGTH_LONG).show()
        } else if (input.length < 3) {
            Toast.makeText(activity, "Please Enter More than 3 Characters", Toast.LENGTH_LONG).show()
        } else {
            viewModel.submitCurrentWeatherSearch(input, getMetric())
        }
    }

    /**
     * Checks the SharedPrefs file and returns the user settings for metric or imperial
     * @return Boolean representing whether or not the user has set the value to Metric
     */
    fun getMetric(): Boolean {
        val preferences: SharedPreferences = activity.getSharedPreferences("appSettings", Context.MODE_PRIVATE)
        return preferences.getBoolean("isMetric", false)
    }
}
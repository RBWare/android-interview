package com.png.interview.weather.ui.binder

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.png.interview.weather.ui.viewmodel.ForecastViewModel

/**
 * {@link ViewBinder}
 */
class ForecastFragmentViewBinder(
    private val viewModel: ForecastViewModel,
    private val activity: Activity
    )
{
    val availableWeatherViewData = viewModel.availableForecastLiveData
}
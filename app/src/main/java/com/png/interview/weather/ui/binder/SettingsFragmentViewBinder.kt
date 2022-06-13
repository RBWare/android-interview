package com.png.interview.weather.ui.binder

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.png.interview.weather.ui.viewmodel.CurrentWeatherViewModel

/**
 * {@link ViewBinder}
 */
class SettingsFragmentViewBinder(
    activity: Activity,
) {
    private val preferences: SharedPreferences = activity.getSharedPreferences("appSettings", Context.MODE_PRIVATE)

    /**
     * Handles the "Refresh" button action
     */
    fun imperialClicked() {
        preferences.edit().putBoolean("isMetric", false).apply()
    }

    /**
     * Handles the "See Forecast" button action
     */
    fun metricClicked() {
        preferences.edit().putBoolean("isMetric", true).apply()
    }
}
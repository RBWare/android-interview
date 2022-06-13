package com.png.interview.weather.ui.fragment

import android.R
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.png.interview.databinding.FragmentCurrentWeatherBinding
import com.png.interview.ui.InjectedFragment
import com.png.interview.weather.ui.binder.CurrentWeatherFragmentViewBinder
import com.png.interview.weather.ui.viewmodel.CurrentWeatherViewModel


class CurrentWeatherFragment : InjectedFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentCurrentWeatherBinding.inflate(inflater, container,false).apply {
            viewBinder = CurrentWeatherFragmentViewBinder(
                getViewModel(),
                requireActivity(),
                settingsAction = {
                    findNavController().navigate(CurrentWeatherFragmentDirections.actionCurrentWeatherFragmentToSettingsFragment())
                },
                forecastAction = { lastSearch: String, isMetric: Boolean ->
                    findNavController().navigate(CurrentWeatherFragmentDirections.actionCurrentWeatherFragmentToForecastFragment(lastSearch, isMetric))
                }
            )

            this.actvAutoComplete.doAfterTextChanged { searchQuery ->
                if (searchQuery == null || searchQuery.length < 3) {
                    return@doAfterTextChanged
                }
                this.actvAutoComplete.isVisible = true
                getViewModel<CurrentWeatherViewModel>().autoCompleteSearch(searchQuery.toString())
            }

            getViewModel<CurrentWeatherViewModel>().availableAutoCompleteLiveData.observe(viewLifecycleOwner) {
                val autoCompleteList: MutableList<String> = ArrayList()

                it?.forEach { autocompleteViewData ->
                    autoCompleteList.add(autocompleteViewData)
                }

                val adapter = context?.let { it1 ->
                    ArrayAdapter(
                        it1,
                        R.layout.simple_list_item_1, autoCompleteList
                    )
                }

                this.actvAutoComplete.setAdapter(adapter)
            }
            this.actvAutoComplete.setOnItemClickListener{ parent, view, _, _ ->

                val searchQuery: String = (view as TextView).text.toString()
                val preferences: SharedPreferences = requireActivity().getSharedPreferences("appSettings", Context.MODE_PRIVATE)
                val isMetric: Boolean =  preferences.getBoolean("isMetric", false)

                getViewModel<CurrentWeatherViewModel>().submitCurrentWeatherSearch(searchQuery, isMetric)
                this.actvAutoComplete.text.clear()
                this.actvAutoComplete.isVisible = false
                this.btnGo.isInvisible = true

                // Hide keyboard
                val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(parent.windowToken, 0)
            }

            this.lifecycleOwner = viewLifecycleOwner
        }.root
    }
}
package com.png.interview.weather.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.png.interview.databinding.FragmentForecastBinding
import com.png.interview.ui.InjectedFragment
import com.png.interview.weather.ui.binder.ForecastFragmentViewBinder
import com.png.interview.weather.ui.viewmodel.ForecastViewModel

class ForecastFragment : InjectedFragment() {
    private val args: ForecastFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentForecastBinding.inflate(inflater, container,false).apply {
            viewBinder = ForecastFragmentViewBinder(
                getViewModel(),
                requireActivity()
            )
            this.lifecycleOwner = viewLifecycleOwner
            getViewModel<ForecastViewModel>().submitDailyForecastSearch(args.lastSearch, args.isMetric)
        }.root
    }

    override fun onResume() {
        super.onResume()


    }
}
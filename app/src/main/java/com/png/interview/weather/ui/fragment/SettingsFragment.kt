package com.png.interview.weather.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.png.interview.databinding.FragmentSettingsBinding
import com.png.interview.ui.InjectedFragment
import com.png.interview.weather.ui.binder.SettingsFragmentViewBinder

class SettingsFragment : InjectedFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentSettingsBinding.inflate(inflater, container,false).apply {
            viewBinder = SettingsFragmentViewBinder(
                requireActivity()
            )
            this.lifecycleOwner = viewLifecycleOwner
        }.root
    }
}
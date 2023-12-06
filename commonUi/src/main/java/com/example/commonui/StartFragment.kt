package com.example.commonui

import android.os.Bundle
import com.example.navigation.NavigationFlow
import com.example.navigation.ToFlowNavigatable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFragment : BaseFragment(R.layout.fragment_start) {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as ToFlowNavigatable).navigateToFlow(NavigationFlow.ContentFlow)
    }
}
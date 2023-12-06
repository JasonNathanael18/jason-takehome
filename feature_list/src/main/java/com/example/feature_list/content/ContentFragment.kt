package com.example.feature_list.content

import androidx.viewpager.widget.ViewPager
import com.example.commonui.BaseFragment
import com.example.feature_list.R
import com.example.feature_list.databinding.FragmentContentBinding
import com.example.feature_list.favourite.FavouriteFragment
import com.example.feature_list.popular.PopularFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContentFragment : BaseFragment(R.layout.fragment_content) {

    private lateinit var binding: FragmentContentBinding

    override fun initComponent() {
        super.initComponent()
        binding = FragmentContentBinding.bind(requireView())
        setupViewPager(binding.viewPager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }


    private fun setupViewPager(viewpager: ViewPager) {
        var adapter = ContentAdapter(childFragmentManager)

        // LoginFragment is the name of Fragment and the Login
        // is a title of tab
        adapter.addFragment(PopularFragment(), "Popular")
        adapter.addFragment(FavouriteFragment(), "Favourite")

        // setting adapter to view pager.
        viewpager.adapter = adapter
    }
}
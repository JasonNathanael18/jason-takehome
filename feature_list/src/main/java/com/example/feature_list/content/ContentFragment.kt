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
        val adapter = ContentAdapter(childFragmentManager)
        adapter.addFragment(PopularFragment(), "Popular")
        adapter.addFragment(FavouriteFragment(), "Favourite")
        viewpager.adapter = adapter
    }
}
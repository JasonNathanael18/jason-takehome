package com.example.feature_list.favourite

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.commonui.BaseFragment
import com.example.domain.model.User
import com.example.feature_list.R
import com.example.feature_list.databinding.FragmentFavouriteBinding
import com.example.navigation.NavigationFlow
import com.example.navigation.ToFlowNavigatable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FavouriteFragment : BaseFragment(R.layout.fragment_favourite),
    FavouriteAdapter.OnItemClickListener {
    @Inject
    lateinit var adapter: FavouriteAdapter

    private lateinit var binding: FragmentFavouriteBinding
    private val viewModel: FavouriteViewModel by viewModels()

    override fun initComponent() {
        super.initComponent()
        binding = FragmentFavouriteBinding.bind(requireView())

        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvFavouriteList.setLayoutManager(layoutManager)
        binding.rvFavouriteList.setAdapter(adapter)
        binding.rvFavouriteList.initialHideList()

        adapter.clearData()
        viewModel.getFavouriteList()

        adapter.setOnItemClickListener(this)
    }

    override fun initObserver() {
        super.initObserver()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when {
                        it.isLoading -> {
                            binding.rvFavouriteList.showWait()
                        }

                        it is FavouriteUiState.HasFavouriteList -> {
                            adapter.addData(it.mealList)
                            binding.rvFavouriteList.apply {
                                hideWait()
                                showData()
                            }
                        }

                        it is FavouriteUiState.FavouriteListEmpty -> {
                            if (it.error.isNotEmpty()) {
                                binding.rvFavouriteList.apply {
                                    hideWait()
                                    showEmpty("No Data Found")
                                }
                            } else {
                                binding.rvFavouriteList.apply {
                                    if (!it.isLoading) {
                                        hideWait()
                                        showEmpty("No Data Found")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onItemClick(view: View, item: User) {
        (requireActivity() as ToFlowNavigatable).navigateToFlow(NavigationFlow.DetailFlow(item.login))
    }

}
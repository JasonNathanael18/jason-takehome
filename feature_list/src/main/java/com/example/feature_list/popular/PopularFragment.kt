package com.example.feature_list.popular

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.commonui.BaseFragment
import com.example.commonui.component.CompRecyclerView
import com.example.feature_list.R
import com.example.feature_list.databinding.FragmentPopularBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PopularFragment : BaseFragment(R.layout.fragment_popular),
    CompRecyclerView.LoadMoreListener{

    @Inject
    lateinit var adapter: PopularAdapter

    private lateinit var binding: FragmentPopularBinding
    private val viewModel: PopularViewModel by viewModels()

    override fun initComponent() {
        super.initComponent()
        binding = FragmentPopularBinding.bind(requireView())

        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvPopularList.setLayoutManager(layoutManager)
        binding.rvPopularList.setAdapter(adapter)
        binding.rvPopularList.initialHideList()

        adapter.clearData()
        viewModel.getUserList()
    }

    override fun initEventListener() {
        super.initEventListener()
        binding.rvPopularList.listener = this

        binding.compSearchBox.onSearchPerformed { query ->
            adapter.clearData()
            hideKeyboard()
            binding.rvPopularList.showWait()
            viewModel.requestSearch(query)
        }
    }

    override fun initObserver() {
        super.initObserver()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when {
                        it.isLoading -> {
                            binding.rvPopularList.showWait()
                        }

                        it is MealUiState.HasMealList -> {
                            adapter.addData(it.mealList)
                            binding.rvPopularList.apply {
                                hideWait()
                                showData()
                            }
                        }

                        it is MealUiState.MealListEmpty -> {
                            if (it.error.isNotEmpty()) {
                                binding.rvPopularList.apply {
                                    hideWait()
                                    showEmpty("No Data Found")
                                }
                            } else {
                                binding.rvPopularList.apply {
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

    override fun onMoreRequest() {
        viewModel.requestMore()
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}
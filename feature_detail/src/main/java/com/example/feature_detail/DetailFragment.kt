package com.example.feature_detail

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.commonui.BaseFragment
import com.example.domain.model.User
import com.example.feature_detail.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseFragment(R.layout.fragment_detail) {

    private val detailFragmentArgs: DetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    private var currentUser: User? = null

    override fun initComponent() {
        super.initComponent()

        binding = FragmentDetailBinding.bind(requireView())

        detailFragmentArgs.msg.let {
            if (it.isNotEmpty()) {
                viewModel.getUserDetail(detailFragmentArgs.msg)
            }
        }
    }

    override fun initEventListener() {
        super.initEventListener()
        binding.btnAddFavourite.setOnClickListener {
            currentUser?.let { data ->
                viewModel.addNewFavourite(data)
            }
        }
    }

    override fun initObserver() {
        super.initObserver()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when {
                        it.isLoading -> {
                            binding.pbLoading.visibility = View.VISIBLE
                        }

                        it is DetailUiState.HasDetail -> {
                            binding.layoutContent.visibility = View.VISIBLE
                            binding.pbLoading.visibility = View.GONE
                            currentUser = it.userData
                            setupView(it.userData)
                        }

                        it is DetailUiState.DetailNotFound -> {
                            binding.pbLoading.visibility = View.GONE
                            binding.tvNotFound.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun setupView(data: User) {
        val glideOpt = RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE).fitCenter()
            .placeholder(com.example.commonui.R.drawable.ic_launcher_background)
            .error(com.example.commonui.R.drawable.ic_launcher_background)
        Glide.with(requireContext())
            .load(data.avatarUrl)
            .apply(glideOpt)
            .thumbnail(0.1f)
            .into(binding.ivAvatar)
        binding.apply {
            tvName.text = data.name
            tvCompany.text = data.company
            tvEmail.text = data.email
            tvLocation.text = data.location
        }
    }

}
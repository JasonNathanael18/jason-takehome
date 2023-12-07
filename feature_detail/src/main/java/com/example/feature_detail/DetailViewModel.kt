package com.example.feature_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.User
import com.example.domain.usecase.FavouriteUseCase
import com.example.domain.usecase.GetUser
import com.example.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getUser: GetUser,
    private val favouriteUseCase: FavouriteUseCase
) : ViewModel() {
    private val viewModelState = MutableStateFlow(DetailViewModelState(isLoading = true))

    val uiState = viewModelState
        .map(DetailViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    fun getUserDetail(userName: String) {
        viewModelScope.launch {
            getUser.getUserDetail(
                userName
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        viewModelState.update {
                            it.copy(error = "", isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let { list ->
                            viewModelState.update {
                                it.copy(data = list, isLoading = false)
                            }
                        }
                    }
                    is Resource.Error -> {
                        viewModelState.update {
                            it.copy(
                                error = result.message ?: "",
                                data = null,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun addNewFavourite(data: User) {
        viewModelScope.launch {
            favouriteUseCase.insertNewFavouriteData(data)
        }
    }

}

private data class DetailViewModelState(
    val isLoading: Boolean = false,
    val error: String = "",
    val data: User? = null
) {
    fun toUiState(): DetailUiState =
        if (data == null) DetailUiState.DetailNotFound(
            isLoading = isLoading,
            error = error,
        )
        else DetailUiState.HasDetail(isLoading = isLoading, error = error, userData = data)
}

sealed interface DetailUiState {
    val isLoading: Boolean
    val error: String

    data class HasDetail(
        val userData: User,
        override val isLoading: Boolean,
        override val error: String
    ) : DetailUiState

    data class DetailNotFound(
        override val isLoading: Boolean,
        override val error: String
    ) : DetailUiState
}
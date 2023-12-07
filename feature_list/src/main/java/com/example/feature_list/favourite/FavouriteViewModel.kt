package com.example.feature_list.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.User
import com.example.domain.usecase.FavouriteUseCase
import com.example.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val favouriteUseCase: FavouriteUseCase
) : ViewModel() {

    private val viewModelState = MutableStateFlow(FavouriteViewModelState(isLoading = true))

    val uiState = viewModelState
        .map(FavouriteViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    fun getFavouriteList() {
        viewModelScope.launch {
            favouriteUseCase.getFavouriteList().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        viewModelState.update {
                            it.copy(error = "", isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let { list ->
                            viewModelState.update {
                                it.copy(mealList = list, isLoading = false)
                            }
                        }
                    }
                    is Resource.Error -> {
                        viewModelState.update {
                            it.copy(
                                error = result.message ?: "",
                                mealList = listOf(),
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

}

private data class FavouriteViewModelState(
    val isLoading: Boolean = false,
    val error: String = "",
    val mealList: List<User> = listOf(),
    val isPreviousPageLoaded: Boolean = false
) {
    fun toUiState(): FavouriteUiState =
        if (mealList.isEmpty()) FavouriteUiState.FavouriteListEmpty(
            isLoading = isLoading,
            error = error,
            isPreviousPageLoaded = isPreviousPageLoaded
        )
        else FavouriteUiState.HasFavouriteList(isLoading = isLoading, error = error, mealList = mealList)
}

sealed interface FavouriteUiState {
    val isLoading: Boolean
    val error: String

    data class HasFavouriteList(
        val mealList: List<User>,
        override val isLoading: Boolean,
        override val error: String
    ) : FavouriteUiState

    data class FavouriteListEmpty(
        override val isLoading: Boolean,
        override val error: String,
        val isPreviousPageLoaded: Boolean
    ) : FavouriteUiState
}
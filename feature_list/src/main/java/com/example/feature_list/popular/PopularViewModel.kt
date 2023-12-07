package com.example.feature_list.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.User
import com.example.domain.usecase.GetUser
import com.example.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val getUser: GetUser,
) : ViewModel() {

    private val viewModelState = MutableStateFlow(PopularViewModelState(isLoading = true))

    val uiState = viewModelState
        .map(PopularViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    var query = "jason"

    private var nextPageToLoad: Int = 1

    fun getUserList() {
        viewModelScope.launch {
            getUser.getUserList(
                query,
                nextPageToLoad
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
                                it.copy(userList = list, isLoading = false)
                            }
                        }
                    }
                    is Resource.Error -> {
                        viewModelState.update {
                            it.copy(
                                error = result.message ?: "",
                                userList = listOf(),
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun requestSearch(query: String) {
        nextPageToLoad = 1
        this.query = query
        getUserList()
    }

    fun requestMore() {
        nextPageToLoad++
        getUserList()
    }

}

private data class PopularViewModelState(
    val isLoading: Boolean = false,
    val error: String = "",
    val userList: List<User> = listOf()
) {
    fun toUiState(): PopularUiState =
        if (userList.isEmpty()) PopularUiState.PopularListEmpty(
            isLoading = isLoading,
            error = error
        )
        else PopularUiState.HasPopularList(isLoading = isLoading, error = error, userList = userList)
}

sealed interface PopularUiState {
    val isLoading: Boolean
    val error: String

    data class HasPopularList(
        val userList: List<User>,
        override val isLoading: Boolean,
        override val error: String
    ) : PopularUiState

    data class PopularListEmpty(
        override val isLoading: Boolean,
        override val error: String
    ) : PopularUiState
}
package com.example.feature_list.favourite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.domain.model.User
import com.example.domain.usecase.FavouriteUseCase
import com.example.domain.utils.Resource
import com.example.feature_list.TestCoroutineRule
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class FavouriteViewModelTest : TestCase() {
    @get:Rule
    var taskRule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var favouriteUseCase: FavouriteUseCase

    @Test
    fun `when get favourite return SUCCESS and empty list`() {
        kotlinx.coroutines.test.runTest {
            val responseList: List<User> = listOf()
            Mockito.doReturn(flowOf(Resource.Success(responseList))).`when`(favouriteUseCase)
                .getFavouriteList()
            val viewModel =
                FavouriteViewModel(favouriteUseCase)
            viewModel.getFavouriteList()
            viewModel.uiState.test {
                assertEquals(
                    FavouriteUiState.FavouriteListEmpty(
                        isLoading = false,
                        error = "",
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `when get favourite return SUCCESS and has list`() {
        kotlinx.coroutines.test.runTest {
            val responseList: List<User> = listOf(
                User(
                    login = "login",
                    avatarUrl = "avatarUrl",
                    name = "name",
                    company = "company",
                    email = "email",
                    location = "location"
                )
            )
            Mockito.doReturn(flowOf(Resource.Success(responseList))).`when`(favouriteUseCase)
                .getFavouriteList()
            val viewModel =
                FavouriteViewModel(favouriteUseCase)
            viewModel.getFavouriteList()
            viewModel.uiState.test {
                assertEquals(
                    FavouriteUiState.HasFavouriteList(
                        isLoading = false,
                        error = "",
                        favouriteList = responseList
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `when get favourite should return Loading`() {
        kotlinx.coroutines.test.runTest {
            val responseList: List<User> = listOf()
            Mockito.doReturn(flowOf(Resource.Loading(responseList))).`when`(favouriteUseCase)
                .getFavouriteList()
            val viewModel =
                FavouriteViewModel(favouriteUseCase)
            viewModel.getFavouriteList()
            viewModel.uiState.test {
                assertEquals(
                    FavouriteUiState.FavouriteListEmpty(
                        error = "",
                        isLoading = true,
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `when get favourite should return ERROR`() {
        kotlinx.coroutines.test.runTest {
            val responseList: List<User> = listOf()
            Mockito.doReturn(flowOf(Resource.Error(message = "error", responseList))).`when`(favouriteUseCase)
                .getFavouriteList()
            val viewModel =
                FavouriteViewModel(favouriteUseCase)
            viewModel.getFavouriteList()
            viewModel.uiState.test {
                assertEquals(
                    FavouriteUiState.FavouriteListEmpty(
                        isLoading = false,
                        error = "error"
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

}
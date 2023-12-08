package com.example.feature_detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.domain.model.User
import com.example.domain.usecase.FavouriteUseCase
import com.example.domain.usecase.GetUser
import com.example.domain.utils.Resource
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
class DetailViewModelTest : TestCase() {
    @get:Rule
    var taskRule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var getUser: GetUser

    @Mock
    private lateinit var favouriteUseCase: FavouriteUseCase

    @Test
    fun `when get user return SUCCESS and has list`() {
        kotlinx.coroutines.test.runTest {
            val response =
                User(
                    login = "login",
                    avatarUrl = "avatarUrl",
                    name = "name",
                    company = "company",
                    email = "email",
                    location = "location"
                )
            Mockito.doReturn(flowOf(Resource.Success(response))).`when`(getUser)
                .getUserDetail("jason")
            val viewModel =
                DetailViewModel(getUser, favouriteUseCase)
            viewModel.getUserDetail("jason")
            viewModel.uiState.test {
                assertEquals(
                    DetailUiState.HasDetail(
                        isLoading = false,
                        error = "",
                        userData = response
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `when getUserDetail should return Loading`() {
        kotlinx.coroutines.test.runTest {
            val response: User? = null
            Mockito.doReturn(flowOf(Resource.Loading(response))).`when`(getUser)
                .getUserDetail("jason")
            val viewModel =
                DetailViewModel(getUser, favouriteUseCase)
            viewModel.getUserDetail("jason")
            viewModel.uiState.test {
                assertEquals(
                    DetailUiState.DetailNotFound(
                        error = "",
                        isLoading = true,
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `when getUserDetail should return ERROR`() {
        kotlinx.coroutines.test.runTest {
            val response: User? = null
            Mockito.doReturn(flowOf(Resource.Error(message = "error", response))).`when`(getUser)
                .getUserDetail("jason")
            val viewModel =
                DetailViewModel(getUser, favouriteUseCase)
            viewModel.getUserDetail("jason")
            viewModel.uiState.test {
                assertEquals(
                    DetailUiState.DetailNotFound(
                        isLoading = false,
                        error = "error"
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `when addNewFavourite should execute`() {
        kotlinx.coroutines.test.runTest {
            val data =
                User(
                    login = "login",
                    avatarUrl = "avatarUrl",
                    name = "name",
                    company = "company",
                    email = "email",
                    location = "location"
                )
            val viewModel =
                DetailViewModel(getUser, favouriteUseCase)
            viewModel.addNewFavourite(data)
        }
    }
}
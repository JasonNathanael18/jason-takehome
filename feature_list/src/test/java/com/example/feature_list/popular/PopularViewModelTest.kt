package com.example.feature_list.popular

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.domain.model.User
import com.example.domain.usecase.GetUser
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
class PopularViewModelTest : TestCase() {
    @get:Rule
    var taskRule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var getUser: GetUser

    @Test
    fun `when get user return SUCCESS and empty list`() {
        kotlinx.coroutines.test.runTest {
            val responseList: List<User> = listOf()
            Mockito.doReturn(flowOf(Resource.Success(responseList))).`when`(getUser)
                .getUserList("jason", 1)
            val viewModel =
                PopularViewModel(getUser)
            viewModel.getUserList()
            viewModel.uiState.test {
                assertEquals(
                    PopularUiState.PopularListEmpty(
                        isLoading = false,
                        error = "",
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `when get user return SUCCESS and has list`() {
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
            Mockito.doReturn(flowOf(Resource.Success(responseList))).`when`(getUser)
                .getUserList("jason", 1)
            val viewModel =
                PopularViewModel(getUser)
            viewModel.getUserList()
            viewModel.uiState.test {
                assertEquals(
                    PopularUiState.HasPopularList(
                        isLoading = false,
                        error = "",
                        userList = responseList
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `when requestSearch should return Loading`() {
        kotlinx.coroutines.test.runTest {
            val responseList: List<User> = listOf()
            Mockito.doReturn(flowOf(Resource.Loading(responseList))).`when`(getUser)
                .getUserList("a", 1)
            val viewModel =
                PopularViewModel(getUser)
            viewModel.requestSearch("a")
            viewModel.uiState.test {
                assertEquals(
                    PopularUiState.PopularListEmpty(
                        error = "",
                        isLoading = true,
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `when requestSearch should return ERROR`() {
        kotlinx.coroutines.test.runTest {
            val responseList: List<User> = listOf()
            Mockito.doReturn(flowOf(Resource.Error(message = "error", responseList))).`when`(getUser)
                .getUserList("a", 1)
            val viewModel =
                PopularViewModel(getUser)
            viewModel.requestSearch("a")
            viewModel.uiState.test {
                assertEquals(
                    PopularUiState.PopularListEmpty(
                        isLoading = false,
                        error = "error"
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }
}
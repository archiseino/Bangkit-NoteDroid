package com.example.projectsubmissionandroidfundamentalbangkit.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.projectsubmissionandroidfundamentalbangkit.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    private val userRepository : UserRepository = mockk()
    private lateinit var viewModel : DetailViewModel
    private val user = "Xenoare"
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val rule : TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel  = DetailViewModel(userRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `I don't know this is will work or not`() {
        val message = "Welp, this is error"
        coEvery {
            userRepository.isUserFavorite(any())
        } throws IllegalAccessException(message)

        viewModel.isFavoritesUser(user)

        coVerify {
            userRepository.isUserFavorite(user)
        }
        Assert.assertEquals(viewModel.isFavorite.value, 1)

    }

}
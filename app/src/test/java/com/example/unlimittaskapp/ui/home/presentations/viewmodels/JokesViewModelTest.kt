package com.example.unlimittaskapp.ui.home.presentations.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.unlimittaskapp.CommonUtils
import com.example.unlimittaskapp.data.database.module.Jokes
import com.example.unlimittaskapp.remote.response.AppResponse
import com.example.unlimittaskapp.remote.response.ResponseStatus
import com.example.unlimittaskapp.ui.home.domain.usecases.HomeUseCase
import com.example.unlimittaskapp.util.getOrAwaitValue
import io.realm.RealmList
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class JokesViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var homeUseCase: HomeUseCase

    private  var viewModel: JokesViewModel?= null

    private val testDispatcher = StandardTestDispatcher()

    @Before
    @Throws(Exception::class)
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = JokesViewModel(homeUseCase)

    }

    @Test
    fun testGetJokes() = runTest {
        val jokesData = Jokes("Owner 1", "1" )
        val result = AppResponse.Success(jokesData)
        `when`(homeUseCase.callJokesApis()).thenReturn(result)
        viewModel?.getJokes()
        testDispatcher.scheduler.advanceUntilIdle()
        val repositories = viewModel?.jokesListLiveData?.getOrAwaitValue()
        assertEquals(result, repositories)
    }
    @Test
    fun testGetJokesError() = runTest {
        val result = AppResponse.Error(203, CommonUtils.getHttpErrorMessage(ResponseStatus.NO_DATA))
        `when`(homeUseCase.callJokesApis()).thenReturn(result)
        viewModel?.getJokes()
        testDispatcher.scheduler.advanceUntilIdle()
        val repositories = viewModel?.jokesListLiveData?.getOrAwaitValue()
        assertEquals(result, repositories)
    }
    @Test
    fun testGetJokesOffline() = runTest {
        val list = RealmList(Jokes("Owner 1", "1" ),Jokes("Owner 2", "2"))
        val result = AppResponse.Success(list)
        `when`(homeUseCase.getJokesList()).thenReturn(result)
        viewModel?.getJokesDataFromLocalDB()
        testDispatcher.scheduler.advanceUntilIdle()
        val repositories = viewModel?.jokesListLiveDataDb?.getOrAwaitValue()
        assertEquals(result, repositories)
    }
    @Test
    fun testGetJokesOfflineEmpty() = runTest {
        val list = RealmList<Jokes>()
        val result = AppResponse.Success(list)
        `when`(homeUseCase.getJokesList()).thenReturn(result)
        viewModel?.getJokesDataFromLocalDB()
        testDispatcher.scheduler.advanceUntilIdle()
        val repositories = viewModel?.jokesListLiveDataDb?.getOrAwaitValue()
        assertEquals(result, repositories)
    }
    @Test
    fun testGetJokesOfflineError() = runTest {
        val error=  AppResponse.Error(203, CommonUtils.getHttpErrorMessage(ResponseStatus.NO_DATA))
        `when`(homeUseCase.getJokesList()).thenReturn(error)
        viewModel?.getJokesDataFromLocalDB()
        testDispatcher.scheduler.advanceUntilIdle()
        val repositories = viewModel?.jokesListLiveDataDb?.getOrAwaitValue()
        assertEquals(error, repositories)
    }

    @After
    fun close() {
        Dispatchers.shutdown()
        viewModel = null
    }
}

package com.example.unlimittaskapp.ui.home.data.repositoryImpl

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.unlimittaskapp.data.database.module.Jokes
import com.example.unlimittaskapp.remote.response.AppResponse
import com.example.unlimittaskapp.ui.home.data.services.HomeComponentService
import io.realm.RealmList
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class HomeRepositoryImplTest{
     @get:Rule
     val rule = InstantTaskExecutorRule()

     @Mock
     private lateinit var homeComponentService: HomeComponentService

     private var homeRepositoryImpl: HomeRepositoryImpl? = null


     @Before
     fun setup() {
         MockitoAnnotations.openMocks(this)
         homeRepositoryImpl = HomeRepositoryImpl(homeComponentService)
     }

     @Test
     fun testGetEmptyError() = runBlocking {
         val response = AppResponse.Error(    203,"No Data Found")
         Mockito.`when`(homeComponentService.getJokesLists("json")).thenReturn(response)

         // Call the method under test
         val result = homeRepositoryImpl?.getJokesLists("json")
         // Verify that the result matches the expected repositories
         TestCase.assertEquals(response, result)
     }
     @Test
     fun testGetEmptySuccess() = runBlocking {
         val jokesData = Jokes("Owner 1", "1" )
         val data = AppResponse.Success(jokesData)
         Mockito.`when`(homeComponentService.getJokesLists("json")).thenReturn(data)

         // Call the method under test
         val result = homeRepositoryImpl?.getJokesLists("json")
         // Verify that the result matches the expected repositories
         TestCase.assertEquals(data, result)
     }
     @Test
     fun testGetJokesOffline() = runTest {
         val list = RealmList(Jokes("Owner 1", "1" ), Jokes("Owner 2", "2"))
         val result = AppResponse.Success(list)
         Mockito.`when`(homeComponentService.getJokesListFromDb()).thenReturn(result)
         val response = homeRepositoryImpl?.getJokesListsFromDb()
         TestCase.assertEquals(result, response)
     }
     @Test
     fun testGetJokesOfflineEmpty() = runTest {
         val list = RealmList<Jokes>()
         val result = AppResponse.Success(list)
         Mockito.`when`(homeComponentService.getJokesListFromDb()).thenReturn(result)
         val response = homeRepositoryImpl?.getJokesListsFromDb()
         TestCase.assertEquals(result, response)
     }
     @After
     fun close() {
         Dispatchers.shutdown()
         homeRepositoryImpl = null
     }
 }
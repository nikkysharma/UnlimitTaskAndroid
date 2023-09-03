package com.example.unlimittaskapp.ui.home.data.services

import com.example.unlimittaskapp.base.BaseRepository
import com.example.unlimittaskapp.data.database.module.Jokes
import com.example.unlimittaskapp.remote.apinterface.RetrofitApiService
import com.example.unlimittaskapp.remote.response.AppResponse
import com.example.unlimittaskapp.remote.response.JokesData
import javax.inject.Inject

class HomeComponentService
@Inject constructor(
    private val retrofitApi: RetrofitApiService
) : BaseRepository() {

    /**
     * get all available staff list*/
    suspend fun getJokesLists(parameter:String): AppResponse<JokesData> {
        return invokeApiCall {
            retrofitApi.getJokesLists(parameter)
        }
    }

}
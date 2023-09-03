package com.example.unlimittaskapp.remote.apinterface

import com.example.unlimittaskapp.constant.AppConstants
import com.example.unlimittaskapp.data.database.module.Jokes
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApiService {
    @GET(AppConstants.GET_JOKES_DATA_URL)
    suspend fun getJokesLists(
        @Query("format") fromDate: String,
    ): Jokes
}
package com.example.unlimittaskapp.remote.apinterface

import com.example.unlimittaskapp.data.database.module.Jokes
import com.example.unlimittaskapp.remote.response.JokesData
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApiService {
    @GET("api")
    suspend fun getJokesLists(
        @Query("format") fromDate: String,
    ): JokesData
}
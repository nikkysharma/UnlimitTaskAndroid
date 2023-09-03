package com.example.unlimittaskapp.ui.home.domain.repository

import com.example.unlimittaskapp.data.database.module.Jokes
import com.example.unlimittaskapp.remote.response.AppResponse

interface HomeRepository {
    suspend fun getJokesLists(parameter:String): AppResponse<Jokes>
}
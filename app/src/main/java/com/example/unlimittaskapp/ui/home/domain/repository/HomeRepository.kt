package com.example.unlimittaskapp.ui.home.domain.repository

import com.example.unlimittaskapp.data.database.module.Jokes
import com.example.unlimittaskapp.remote.response.AppResponse
import io.realm.RealmList

interface HomeRepository {
    suspend fun getJokesLists(parameter:String): AppResponse<Jokes>

    suspend fun insertJokesListInDb(jokes: Jokes)

    suspend fun getJokesListsFromDb(): AppResponse<RealmList<Jokes>>
}
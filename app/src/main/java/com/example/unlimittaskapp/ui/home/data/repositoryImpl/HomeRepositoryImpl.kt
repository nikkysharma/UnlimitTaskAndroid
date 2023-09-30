package com.example.unlimittaskapp.ui.home.data.repositoryImpl

import com.example.unlimittaskapp.data.database.module.Jokes
import com.example.unlimittaskapp.remote.response.AppResponse
import com.example.unlimittaskapp.ui.home.data.services.HomeComponentService
import com.example.unlimittaskapp.ui.home.domain.repository.HomeRepository
import io.realm.RealmList
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeService: HomeComponentService
) : HomeRepository {

    override suspend fun getJokesLists(parameter:String): AppResponse<Jokes> {
        return homeService.getJokesLists(parameter)
    }

    override suspend fun insertJokesListInDb(jokes: Jokes) {
        return homeService.insertJokesInDb(jokes)
    }
    override suspend fun getJokesListsFromDb(): AppResponse<RealmList<Jokes>> {
        return homeService.getJokesListFromDb()
    }
}
package com.example.unlimittaskapp.ui.home.data.repositoryImpl

import com.example.unlimittaskapp.data.database.module.Jokes
import com.example.unlimittaskapp.remote.response.AppResponse
import com.example.unlimittaskapp.remote.response.JokesData
import com.example.unlimittaskapp.ui.home.data.services.HomeComponentService
import com.example.unlimittaskapp.ui.home.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeService: HomeComponentService
) : HomeRepository {

    override suspend fun getJokesLists(parameter:String): AppResponse<JokesData> {
        return homeService.getJokesLists(parameter)
    }
}
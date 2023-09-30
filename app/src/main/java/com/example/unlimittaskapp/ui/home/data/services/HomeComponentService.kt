package com.example.unlimittaskapp.ui.home.data.services

import com.example.unlimittaskapp.base.BaseRepository
import com.example.unlimittaskapp.data.database.module.Jokes
import com.example.unlimittaskapp.remote.apinterface.RetrofitApiService
import com.example.unlimittaskapp.remote.response.AppResponse
import io.realm.Realm
import io.realm.RealmList
import javax.inject.Inject

class HomeComponentService
@Inject constructor(
    private val retrofitApi: RetrofitApiService,
    private var realm: Realm
) : BaseRepository() {

    /**
     * get all available staff list*/
    suspend fun getJokesLists(parameter:String): AppResponse<Jokes> {
        return invokeApiCall {
            retrofitApi.getJokesLists(parameter)
        }
    }

    suspend fun insertJokesInDb(jokesData: Jokes) {
        realm.executeTransaction {
            it.insertOrUpdate(jokesData)
        }
    }
    suspend fun getJokesListFromDb() : AppResponse<RealmList<Jokes>> {
        val list = RealmList<Jokes>()
        realm.executeTransaction {
            val item =
                it.where(Jokes::class.java)
                    .findAll()
            val data = it.copyFromRealm(item)
            if (data.size>0){
                data?.let { it1 ->
                    list.addAll(it1)
                }
            }
        }
        return if(list.size>0)
            AppResponse.Success(list)
        else AppResponse.Error(404,"no data available")
    }

}
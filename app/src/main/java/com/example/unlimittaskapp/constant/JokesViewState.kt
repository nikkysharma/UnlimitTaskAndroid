package com.example.unlimittaskapp.constant

import com.example.unlimittaskapp.data.database.module.Jokes
import com.example.unlimittaskapp.remote.response.AppResponse
import com.example.unlimittaskapp.util.BaseViewState
import io.realm.RealmList


class JokesViewState private constructor(data: AppResponse<RealmList<Jokes>>?, currentState: Int, error: Throwable?) :
    BaseViewState<AppResponse<RealmList<Jokes>>>() {
    companion object {
        val list = RealmList(Jokes("Owner 1", "1" ),Jokes("Owner 2", "2"))
        val result = AppResponse.Success(list)
        var ERROR_STATE = JokesViewState(null, State.FAILED.value, Throwable())
        var LOADING_STATE = JokesViewState(null, State.LOADING.value, null)
        var SUCCESS_STATE = JokesViewState(result, State.SUCCESS.value, null)
    }

    init {
        this.data = data
        this.error = error
        this.currentState = currentState
    }
}
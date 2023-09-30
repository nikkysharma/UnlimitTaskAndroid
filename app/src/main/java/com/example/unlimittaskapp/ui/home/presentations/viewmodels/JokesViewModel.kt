package com.example.unlimittaskapp.ui.home.presentations.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.unlimittaskapp.base.BaseViewModel
import com.example.unlimittaskapp.data.database.module.Jokes
import com.example.unlimittaskapp.remote.response.AppResponse
import com.example.unlimittaskapp.ui.home.domain.usecases.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.realm.RealmList
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class JokesViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase
) : BaseViewModel<Any?>() {

    @SuppressLint("StaticFieldLeak")
    @JvmField
    @Inject
    @ApplicationContext
    var context: Context? = null
    // Used for live instance hold
    private val _jokesListLiveData: MutableLiveData<AppResponse<Jokes>> =
        MutableLiveData()
    private val _jokesListLiveDataDb: MutableLiveData<AppResponse<RealmList<Jokes>>> =
        MutableLiveData()

    // Used for live instance hold
    val jokesListLiveData: LiveData<AppResponse<Jokes>>
        get() = _jokesListLiveData
    val jokesListLiveDataDb: LiveData<AppResponse<RealmList<Jokes>>>
        get() = _jokesListLiveDataDb
    /**
     *this function is used for call scheduled event list api
     * */
    fun getJokes(): MutableLiveData<AppResponse<Jokes>> {
        viewModelScope.launch {
            _jokesListLiveData.postValue(AppResponse.Loading(true))
            _jokesListLiveData.postValue(homeUseCase.callJokesApis())
        }
        return _jokesListLiveData
    }
    fun getJokesDataFromLocalDB(): MutableLiveData<AppResponse<RealmList<Jokes>>> {
        viewModelScope.launch {
            _jokesListLiveDataDb.postValue(AppResponse.Loading(true))
            _jokesListLiveDataDb.postValue(homeUseCase.getJokesList())
        }
        return _jokesListLiveDataDb
    }
}

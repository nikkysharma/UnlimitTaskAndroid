package com.example.unlimittaskapp.ui.home.domain.usecases

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.unlimittaskapp.DateTimeUtils
import com.example.unlimittaskapp.data.database.module.Jokes
import com.example.unlimittaskapp.remote.response.AppResponse
import com.example.unlimittaskapp.ui.home.domain.repository.HomeRepository
import io.realm.Realm
import io.realm.RealmList
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject


class HomeUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
    private var realm: Realm
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun callJokesApis() : AppResponse<RealmList<Jokes>>{
        val jokesData = homeRepository.getJokesLists("json")
        if (jokesData is AppResponse.Success) {
            val data = jokesData.data.joke.toString()
            val randomId = UUID.randomUUID().toString()
            val jsonData = Jokes()
            jsonData.jokes = data
            jsonData.jokesId = randomId
            val dateLocal: Date = Calendar.getInstance().time
            val df = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val formattedDate: String = df.format(dateLocal)
            jsonData.startDateStr = formattedDate
            realm.executeTransaction {
                    it.insertOrUpdate(jsonData)
            }
        }
        return getJokesList()
    }

    fun getJokesList(
    ): AppResponse<RealmList<Jokes>> {
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

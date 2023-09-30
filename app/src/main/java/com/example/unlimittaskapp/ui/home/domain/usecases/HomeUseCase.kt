package com.example.unlimittaskapp.ui.home.domain.usecases

import com.example.unlimittaskapp.CommonUtils
import com.example.unlimittaskapp.data.database.module.Jokes
import com.example.unlimittaskapp.remote.response.AppResponse
import com.example.unlimittaskapp.remote.response.ResponseStatus
import com.example.unlimittaskapp.ui.home.domain.repository.HomeRepository
import io.realm.RealmList
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject


class HomeUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend fun callJokesApis() : AppResponse<Jokes>{
        val jokesData = homeRepository.getJokesLists("json")
        if (jokesData is AppResponse.Success) {
            val newlyCreatedData = createJokesData(jokesData.data.toString())
            insertDataIntoLocalDataBase(newlyCreatedData)
            return jokesData
        }
        return jokesData
    }

    private suspend fun insertDataIntoLocalDataBase(jokesData: Jokes) {
         homeRepository.insertJokesListInDb(jokesData)
    }


    private fun createJokesData(data:String) : Jokes {
        val randomId = UUID.randomUUID().toString()
        val jsonData = Jokes()
        jsonData.jokes = data
        jsonData.jokesId = randomId
        val dateLocal: Date = Calendar.getInstance().time
        val df = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val formattedDate: String = df.format(dateLocal)
        jsonData.startDateStr = formattedDate
        jsonData.startDate = dateLocal
        return  jsonData
    }

    suspend fun getJokesList(
    ): AppResponse<RealmList<Jokes>> {
       return homeRepository.getJokesListsFromDb()
    }

}

package com.example.unlimittaskapp.base

import android.util.Log
import com.example.unlimittaskapp.CommonUtils
import com.example.unlimittaskapp.constant.AppConstants
import com.example.unlimittaskapp.remote.response.AppResponse
import com.example.unlimittaskapp.remote.response.ResponseStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.HttpException
import java.net.SocketTimeoutException

abstract class BaseRepository {
    suspend fun <T> invokeApiCall(
        apiCall: suspend () -> T
    ): AppResponse<T> {
        return withContext(Dispatchers.IO) {
            try {
                AppResponse.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                Log.d("BaseRepository->Exception", throwable.toString())
                when (throwable) {
                    is SocketTimeoutException -> {
                        AppResponse.Error(
                            1500,
                            CommonUtils.getHttpErrorMessage(ResponseStatus.TIMEOUT)
                        )
                    }
                    is HttpException -> {
                        val error = throwable.response()?.errorBody()?.string()
                        Log.d("errorrr", "error=$error")
                        if (throwable.code() == 401 || throwable.code() == 500 || throwable.code() == 400 || throwable.code() == 403 || throwable.code() == 404 && (error!!.contains(
                                AppConstants.MSG_KEY
                            ) || error.contains(AppConstants.MESSAGE))
                        ) {
                            AppResponse.Error(throwable.code(), getErrorMessage(error))
                        } else {
                            AppResponse.Error(
                                throwable.code(),
                                CommonUtils.getHttpErrorMessage(throwable.code())
                            )
                        }
                    }
                    else -> {
                        AppResponse.Error(
                            1000,
                            CommonUtils.getHttpErrorMessage(ResponseStatus.NO_INTERNET)
                        )
                    }
                }
            }
        }
    }

    private fun getErrorMessage(error: String?): String {
        if (error.isNullOrBlank())
            return AppConstants.SOMETHING_WENT_WRONG
        return try {
            val obj = JSONObject(error)
            if (obj.has(AppConstants.DETAIL_KEY)) {
                val array = obj.getJSONArray(AppConstants.DETAIL_KEY)
                convertArrayIntoString(array)
            }
            else if (obj.has(AppConstants.DATA_KEY)) {
                val array = obj.getJSONObject(AppConstants.DATA_KEY).getJSONArray(AppConstants.DETAIL_KEY)
                convertArrayIntoString(array)
            }
            else if (obj.has(AppConstants.MSG_KEY)) {
                obj.optString(AppConstants.MSG_KEY, AppConstants.SOMETHING_WENT_WRONG)
            } else {
                obj.optString(AppConstants.MESSAGE, AppConstants.SOMETHING_WENT_WRONG)
            }
        } catch (e: Exception) {
            AppConstants.SOMETHING_WENT_WRONG
        }
    }

    private fun convertArrayIntoString(array: JSONArray):String{
        var str = ""
        val len = array.length()
        if(AppConstants.ERROR_MESSAGE_DETAIL.isNotEmpty())
            str=AppConstants.ERROR_MESSAGE_DETAIL+"\n\n"
        for (i in 0 until len) {
            if(i == (len-1))
                str += array[i].toString()
            else
                str=str+array[i].toString()+"\n\n"
        }
        AppConstants.ERROR_MESSAGE_DETAIL=str
        return str
    }
}
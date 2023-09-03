package com.example.unlimittaskapp.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.unlimittaskapp.constant.AppConstants
import io.realm.Realm
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.lang.reflect.Field
import java.net.HttpURLConnection

class AuthInterceptor () : Interceptor {
    var alertDialog: AlertDialog? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val requestBuilder = request
            .newBuilder()
        val response: Response = chain.proceed(requestBuilder.build())
        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            response.close()
            var newRequest: Request? = null
            val newResponse = newRequest?.let { chain.proceed(it) }
            if (newResponse == null || newResponse.code == HttpURLConnection.HTTP_UNAUTHORIZED) {

                return response
            }
            return newResponse
        }
        return response
    }


}
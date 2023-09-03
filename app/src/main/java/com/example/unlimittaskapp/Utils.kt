package com.example.unlimittaskapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.unlimittaskapp.remote.response.ResponseStatus

object CommonUtils {

    /**
     * this function is used for get error message from http error code
     * @param context for context
     * */
    fun getHttpErrorMessage(code: Int, context: Context? = null): CharSequence {
        return when (code) {
            ResponseStatus.BAD_REQUEST -> "Bad Request"
            ResponseStatus.UN_AUTHORIZED -> "Unauthorized Access"
            ResponseStatus.TIMEOUT -> "Request TimeOut"
            ResponseStatus.SERVER_ERROR -> "Server Error"
            ResponseStatus.NO_INTERNET -> context?.getText(R.string.no_internet_error)
                ?: "No Internet connection"
            else -> "Something went wrong"
        }
    }

    /**
     * this function is used for check internet is available or not
     * @param context for context
     * */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_VPN")
                return true
            }
        }
        return false
    }



}
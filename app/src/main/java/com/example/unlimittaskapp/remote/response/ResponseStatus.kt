package com.example.unlimittaskapp.remote.response

object ResponseStatus {
    const val ERROR = 0
    const val SUCCESS = 1
    const val LOADING = 2

    const val BAD_REQUEST = 400
    const val UN_AUTHORIZED = 401
    const val TIMEOUT = 408
    const val SERVER_ERROR = 500
    const val NO_INTERNET = 1000
}
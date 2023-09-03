package com.example.unlimittaskapp.remote.response

sealed class AppResponse<out T> {
    //success response
    data class Success<out T>(val data: T) : AppResponse<T>()

    //failure response with message
    data class Error(
        val code: Int,
        val message: CharSequence
    ) : AppResponse<Nothing>()

    //failure response with message for cognito
    data class AuthError(
        val code: Int?,
        val message: CharSequence?
    ) : AppResponse<Nothing>()


    //loading status
    data class Loading( val isLoading: Boolean?) : AppResponse<Nothing>()
}
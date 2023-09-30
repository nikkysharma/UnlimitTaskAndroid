package com.example.unlimittaskapp.util

open class BaseViewState<T> {
    @JvmName("setData1")
    fun setData(data: T) {
        this.data = data
    }
    var error: Throwable? = null

    var data: T? = null
    var currentState = 0

    @JvmName("getCurrentState1")
    private fun getCurrentState(): Int {
        return currentState
    }

    @JvmName("setCurrentState1")
    private fun setCurrentState(currentState: Int) {
        this.currentState = currentState
    }

    @JvmName("setError1")
    fun setError(error: Throwable?) {
        this.error = error
    }

    enum class State(var value: Int) {
        LOADING(0), SUCCESS(1), FAILED(-1);
    }
}
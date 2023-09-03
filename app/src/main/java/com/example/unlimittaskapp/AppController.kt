package com.example.unlimittaskapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm


@HiltAndroidApp
class AppController : Application() {

    companion object {
        var instance: AppController? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Realm.init(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

}
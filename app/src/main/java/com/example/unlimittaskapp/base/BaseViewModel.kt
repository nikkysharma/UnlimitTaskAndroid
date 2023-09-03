package com.example.unlimittaskapp.base

import androidx.lifecycle.ViewModel
import com.example.unlimittaskapp.factory.RealmConfigurationFactory
import io.realm.Realm

abstract class  BaseViewModel<T> : ViewModel() {
    protected var appRealm = Realm.getInstance(RealmConfigurationFactory.createRealmConfiguration())!!

    protected fun refreshRealm(): Realm {
        return Realm.getInstance(RealmConfigurationFactory.createRealmConfiguration())
    }
}
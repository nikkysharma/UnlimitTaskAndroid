package com.example.unlimittaskapp.factory

import com.example.unlimittaskapp.constant.AppConstants
import com.example.unlimittaskapp.data.database.module.UnLimitDBModule
import io.realm.RealmConfiguration

object RealmConfigurationFactory {
    fun createRealmConfiguration(): RealmConfiguration {
        return  RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()//as of now no migration so delete db if migration
            .modules(UnLimitDBModule())
            .allowWritesOnUiThread(true)
            .name(AppConstants.DATABASE_NAME)
            .build()
    }

}
package com.example.unlimittaskapp.di

import android.content.Context
import com.example.unlimittaskapp.factory.RealmConfigurationFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Named

@InstallIn(SingletonComponent::class)
@Module
object TestDatabaseModule {

    @Provides
    @Named("test_realm_config")
    fun provideRealmConfiguration(
        @ApplicationContext
        context: Context
    ): RealmConfiguration {
        Realm.init(context)
        return RealmConfigurationFactory.createRealmConfiguration()
    }

    @Provides
    @Named("test_realm")
    fun provideRealm(
        @Named("test_realm_config")
        configuration: RealmConfiguration
    ): Realm {
        return Realm.getInstance(configuration)
    }

}
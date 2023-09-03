package com.example.unlimittaskapp.di

import com.example.unlimittaskapp.base.MainThreadBus
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module()
object AppModule {

    @Provides
    @Singleton
    fun provideMainThreadBus(): MainThreadBus {
        return MainThreadBus()
    }

}
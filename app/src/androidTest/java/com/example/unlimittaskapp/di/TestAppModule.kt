package com.example.unlimittaskapp.di

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.example.unlimittaskapp.ui.HiltTestRunner
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@InstallIn(SingletonComponent::class)
@Module()
object TestAppModule {

    @Provides
    @Named("appContext")
    fun provideAppContext(): Context{
        return HiltTestRunner().context ?:
        InstrumentationRegistry.getInstrumentation().targetContext
    }

}
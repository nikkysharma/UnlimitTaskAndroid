package com.example.unlimittaskapp.di

import com.example.unlimittaskapp.remote.apinterface.RetrofitApiService
import com.example.unlimittaskapp.ui.home.data.repositoryImpl.HomeRepositoryImpl
import com.example.unlimittaskapp.ui.home.data.services.HomeComponentService
import com.example.unlimittaskapp.ui.home.domain.repository.HomeRepository
import com.example.unlimittaskapp.ui.home.domain.usecases.HomeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.Realm
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeRepositoryModule {
    @Singleton
    @Provides
    fun provideHomeRepository(
        homeService: HomeComponentService,
    ): HomeRepository {
        return HomeRepositoryImpl(
            homeService = homeService,
        )
    }
    @Singleton
    @Provides
    fun provideHomeUseCase(
        homeRepository: HomeRepository
    ): HomeUseCase {
        return HomeUseCase(homeRepository)
    }
    @Singleton
    @Provides
    fun provideHomeService(
        apiService: RetrofitApiService,
        realm: Realm
    ): HomeComponentService {
        return HomeComponentService(apiService,realm)
    }
}
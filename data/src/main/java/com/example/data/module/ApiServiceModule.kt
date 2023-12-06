package com.example.data.module

import com.example.data.source.remote.UserApiService
import com.example.di.qualifier.AppBaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {
    @Provides
    @Singleton
    fun provideApiService(@AppBaseUrl retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }
}
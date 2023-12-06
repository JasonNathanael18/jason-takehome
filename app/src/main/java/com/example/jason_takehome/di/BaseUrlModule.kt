package com.example.jason_takehome.di

import com.example.di.qualifier.AppBaseUrl
import com.example.jason_takehome.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class BaseUrlModule{
    @Provides
    @AppBaseUrl
    fun provideBaseUrl():String = BuildConfig.base_url
}
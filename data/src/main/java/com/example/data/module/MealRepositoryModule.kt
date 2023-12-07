package com.example.data.module

import com.example.data.repository.UserRepositoryImpl
import com.example.data.source.local.roomdb.dao.UserDao
import com.example.data.source.remote.UserApiService
import com.example.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MealRepositoryModule {

    @Provides
    @Singleton
    fun provideMealRepositoryImpl(
        userApiService: UserApiService,
        userDao: UserDao
    ): UserRepository = UserRepositoryImpl(userApiService, userDao)

}
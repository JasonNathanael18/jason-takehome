package com.example.data.module

import android.app.Application
import com.example.data.source.local.roomdb.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MealDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application) = UserDatabase.getDatabase(application)

    @Singleton
    @Provides
    fun provideUserDao(database: UserDatabase) =
        database.getUserDao()
}
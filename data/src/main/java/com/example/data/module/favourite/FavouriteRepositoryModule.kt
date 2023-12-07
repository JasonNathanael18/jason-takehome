package com.example.data.module.favourite

import com.example.data.repository.FavouriteRepositoryImpl
import com.example.data.source.local.roomdb.dao.FavouriteDao
import com.example.domain.repository.FavouriteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FavouriteRepositoryModule {

    @Provides
    @Singleton
    fun provideFavouriteRepositoryImpl(
        favouriteDao: FavouriteDao
    ): FavouriteRepository = FavouriteRepositoryImpl(favouriteDao)

}
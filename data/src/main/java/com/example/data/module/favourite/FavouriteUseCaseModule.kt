package com.example.data.module.favourite

import com.example.domain.repository.FavouriteRepository
import com.example.domain.usecase.FavouriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FavouriteUseCaseModule {

    @Provides
    @Singleton
    fun provideGetFavouriteUseCase(repository: FavouriteRepository): FavouriteUseCase =
        FavouriteUseCase(repository)
}
package com.example.data.module

import com.example.domain.repository.UserRepository
import com.example.domain.usecase.GetUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MealUseCaseModule {

    @Provides
    @Singleton
    fun provideGetMealUseCase(repository: UserRepository): GetUser =
        GetUser(repository)
}
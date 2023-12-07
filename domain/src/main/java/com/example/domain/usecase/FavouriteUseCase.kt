package com.example.domain.usecase

import com.example.domain.model.User
import com.example.domain.repository.FavouriteRepository
import com.example.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

class FavouriteUseCase(
    private val favouriteRepository: FavouriteRepository
) {
    suspend fun insertNewFavouriteData(data: User) {
        favouriteRepository.insertNewFavourite(data)
    }

    fun getFavouriteList(): Flow<Resource<List<User>>> {
        return favouriteRepository.getFavouriteList()
    }
}
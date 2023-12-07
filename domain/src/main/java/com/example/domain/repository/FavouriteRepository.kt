package com.example.domain.repository

import com.example.domain.model.User
import com.example.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {
    suspend fun insertNewFavourite(data: User)
    fun getFavouriteList(): Flow<Resource<List<User>>>
}
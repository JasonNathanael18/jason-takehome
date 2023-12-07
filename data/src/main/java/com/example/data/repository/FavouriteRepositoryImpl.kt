package com.example.data.repository

import com.example.data.source.local.roomdb.converter.FavouriteEntityConverter
import com.example.data.source.local.roomdb.dao.FavouriteDao
import com.example.domain.model.User
import com.example.domain.repository.FavouriteRepository
import com.example.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavouriteRepositoryImpl @Inject constructor(
    private val favouriteDao: FavouriteDao
) : FavouriteRepository {
    override suspend fun insertNewFavourite(data: User) {
        val favEntity = FavouriteEntityConverter.toFavouriteEntity(data)
        favouriteDao.insertNewFavourite(favEntity)
    }

    override fun getFavouriteList(): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading())
        val favUserListEntity = favouriteDao.getFavouriteList()

        if (favUserListEntity.isEmpty()) {
            emit(
                Resource.Error(
                    message = "Oops, data not found"
                )
            )
        } else {
            emit(Resource.Success(favUserListEntity.map { it.toUser() }))
        }
    }

}
package com.example.data.source.local.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.source.local.roomdb.entity.local.FavouriteEntity

@Dao
interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewFavourite(data: FavouriteEntity)

    @Query("SELECT * FROM ${FavouriteEntity.TABLE_NAME}")
    suspend fun getFavouriteList(): List<FavouriteEntity>

}
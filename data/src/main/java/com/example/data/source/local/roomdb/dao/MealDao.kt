package com.example.data.source.local.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.source.local.roomdb.entity.UserEntity

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserList(movies: List<UserEntity>)

    @Query("SELECT * FROM ${UserEntity.TABLE_NAME}")
    suspend fun getUserList(): List<UserEntity>

    @Query("DELETE FROM ${UserEntity.TABLE_NAME}")
    suspend fun deleteAll()
}
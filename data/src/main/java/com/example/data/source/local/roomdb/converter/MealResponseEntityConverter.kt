package com.example.data.source.local.roomdb.converter

import androidx.room.TypeConverter
import com.example.data.source.local.roomdb.entity.UserEntity
import com.example.domain.model.User
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class MealResponseEntityConverter {

    @TypeConverter
    fun fromStringToMovieList(value: String): List<User>? =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
            .adapter<List<User>>(Types.newParameterizedType(List::class.java, User::class.java))
            .fromJson(value)

    @TypeConverter
    fun fromMovieListTypeToString(movieListType: List<User>?): String =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
            .adapter<List<User>>(Types.newParameterizedType(List::class.java, User::class.java))
            .toJson(movieListType)


    @TypeConverter
    fun fromStringToMovieEntityList(value: String): List<UserEntity>? =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build().adapter<List<UserEntity>>(
                Types.newParameterizedType(
                    List::class.java,
                    UserEntity::class.java
                )
            ).fromJson(value)

    @TypeConverter
    fun fromMovieEntityListTypeToString(userEntityListType: List<UserEntity>?): String =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build().adapter<List<UserEntity>>(
                Types.newParameterizedType(
                    List::class.java,
                    UserEntity::class.java
                )
            ).toJson(userEntityListType)
}
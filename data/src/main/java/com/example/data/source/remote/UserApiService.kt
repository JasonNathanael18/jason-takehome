package com.example.data.source.remote

import com.example.data.source.remote.dto.UserDto
import com.example.data.source.remote.dto.UserListResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApiService {
    @GET("search/users")
    suspend fun getUserList(
        @Query("q") query: String,
        @Query("page") page: Int,
    ): UserListResponseDto

    @GET("v1/1/lookup.php")
    suspend fun getMealDetail(
        @Query("i") idMeal: String
    ): UserDto
}
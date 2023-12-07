package com.example.domain.repository

import com.example.domain.model.User
import com.example.domain.utils.Resource
import kotlinx.coroutines.flow.Flow


interface UserRepository {
    fun getUserList(query: String, page: Int): Flow<Resource<List<User>>>
    fun getUserDetail(userName: String): Flow<Resource<User>>
}
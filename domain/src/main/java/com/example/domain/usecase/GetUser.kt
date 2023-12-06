package com.example.domain.usecase

import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import com.example.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

class GetUser(
    private val userRepository: UserRepository
) {

    fun getUserList(
        query: String = "jason",
        page: Int
    ): Flow<Resource<List<User>>> {
        return userRepository.getUserList(query, page)
    }

//    fun getMealDetail(idMeal: String = ""): Flow<Resource<List<User>>> {
//        return userRepository.getMealDetail(idMeal)
//    }
}
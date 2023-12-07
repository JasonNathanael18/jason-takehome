package com.example.data.repository

import com.example.data.source.local.roomdb.dao.UserDao
import com.example.data.source.remote.UserApiService
import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import com.example.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService,
    private val userDao: UserDao
) : UserRepository {
    override fun getUserList(query: String, page: Int): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading())
        try {
            val remoteUserList = userApiService.getUserList(query, page)

            if (!remoteUserList.items.isNullOrEmpty()) {
                val userEntity = remoteUserList.items.map { it.toUserEntity() }
                userDao.insertUserList(userEntity)
                emit(Resource.Success(userEntity.map { it.toUser() }))
            } else {
                emit(
                    Resource.Error(
                        message = "Oops, data not found"
                    )
                )
            }
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong!"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection."
                )
            )
        }
    }

    override fun getUserDetail(userName: String): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        try {
            val remoteUser = userApiService.getUserDetail(userName)

            if (!remoteUser.login.isNullOrEmpty()) {
                val userEntity = remoteUser.toUserEntity()
                userDao.insertUserList(listOf(userEntity))
                emit(Resource.Success(userEntity.toUser()))
            } else {
                emit(
                    Resource.Error(
                        message = "Oops, data not found"
                    )
                )
            }
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong!"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection."
                )
            )
        }
    }

}
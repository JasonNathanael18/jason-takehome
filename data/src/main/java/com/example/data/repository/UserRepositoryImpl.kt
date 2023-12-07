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
                // val mealResponseEntity = remoteUserList.toMealResponseEntity()
//                mealResponseEntity?.let {
//                    mealResponseDao.insertMealResponse(remoteUserList.toMealResponseEntity()!!)
                val userEntity = remoteUserList.items.map { it.toUserEntity() }
                userDao.insertUserList(userEntity) //now insert newly fetched data to db
                emit(Resource.Success(userEntity.map { it.toUser() }))
                //}
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
//        // single source of truth we will emit data from db only and not directly from remote
//        emit(Resource.Success(getMealsFromDb(mealDao)))
    }

    override fun getUserDetail(userName: String): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        try {
            val remoteUser = userApiService.getUserDetail(userName)

            if (!remoteUser.login.isNullOrEmpty()) {
                // val mealResponseEntity = remoteUserList.toMealResponseEntity()
//                mealResponseEntity?.let {
//                    mealResponseDao.insertMealResponse(remoteUserList.toMealResponseEntity()!!)
                val userEntity = remoteUser.toUserEntity()
                userDao.insertUserList(listOf(userEntity)) //now insert newly fetched data to db
                emit(Resource.Success(userEntity.toUser()))
                //}
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
//        // single source of truth we will emit data from db only and not directly from remote
//        emit(Resource.Success(getMealsFromDb(mealDao)))
    }


//    private suspend fun getMealsFromDb(mealDao: MealDao): List<Meal> {
//        val newmeals = mealDao.getUserList().map { it.toUser() }
//        return newmeals
//    }
//
//    override fun getMealDetail(idMeal: String): Flow<Resource<List<Meal>>> = flow {
//        emit(Resource.Loading())
//        try {
//            val remoteMealList = userApiService.getMealDetail(idMeal)
//
//            if (!remoteMealList.results.isNullOrEmpty()) {
//                val mealResponseEntity = remoteMealList.toMealResponseEntity()
//                mealResponseEntity?.let {
//                    mealResponseDao.insertMealResponse(remoteMealList.toMealResponseEntity()!!)
//                    mealDao.insertUserList(remoteMealList.results.map { it.toMealEntity() }) //now insert newly fetched data to db
//                    emit(Resource.Success(mealResponseEntity.toMealList().results))
//                }
//            } else {
//                emit(
//                    Resource.Error(
//                        message = "Oops, data not found"
//                    )
//                )
//            }
//        } catch (e: HttpException) {
//            emit(
//                Resource.Error(
//                    message = "Oops, something went wrong!"
//                )
//            )
//        } catch (e: IOException) {
//            emit(
//                Resource.Error(
//                    message = "Couldn't reach server, check your internet connection."
//                )
//            )
//        }
////        // single source of truth we will emit data from db only and not directly from remote
////        emit(Resource.Success(getMealsFromDb(mealDao)))
//    }

}
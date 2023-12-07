package com.example.data.source.remote.dto

import com.example.data.source.local.roomdb.entity.UserEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDto(

    @Json(name = "login")
    val login: String? = null,

    @Json(name = "avatar_url")
    val avatarUrl: String? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "company")
    val company: String? = null,

    @Json(name = "email")
    val email: String? = null,

    @Json(name = "location")
    val location: String? = null,

    ) {
    fun toUserEntity(): UserEntity {
        return UserEntity(
            login = login.orEmpty(),
            avatarUrl = avatarUrl.orEmpty(),
            name = name.orEmpty(),
            company = company.orEmpty(),
            location = location.orEmpty(),
            email = email.orEmpty()
        )
    }
}

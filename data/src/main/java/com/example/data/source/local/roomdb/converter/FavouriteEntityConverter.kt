package com.example.data.source.local.roomdb.converter

import com.example.data.source.local.roomdb.entity.FavouriteEntity
import com.example.domain.model.User

object FavouriteEntityConverter {

    fun toFavouriteEntity(
        data: User
    ): FavouriteEntity {
        return FavouriteEntity(
            login = data.login,
            avatarUrl = data.avatarUrl,
            name = data.name,
            company = data.company,
            email = data.email,
            location = data.location
        )
    }

}
package com.example.data.source.local.roomdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.User

@Entity(tableName = FavouriteEntity.TABLE_NAME)
data class FavouriteEntity(
    @PrimaryKey val login: String,
    val avatarUrl: String,
    val name: String,
    val company: String,
    val email: String,
    val location: String,
) {
    fun toUser(): User {
        return User(
            login = login,
            avatarUrl = avatarUrl,
            name = name,
            email = email,
            location = location,
            company = company
        )
    }

    companion object {
        const val TABLE_NAME = "favourite"
    }
}
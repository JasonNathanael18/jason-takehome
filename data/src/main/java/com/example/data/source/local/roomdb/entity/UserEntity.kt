package com.example.data.source.local.roomdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.User

@Entity(tableName = UserEntity.TABLE_NAME)
data class UserEntity(
    @PrimaryKey val login: String,
    val avatarUrl: String
) {

    fun toUser(): User {
        return User(
            login = login,
            avatarUrl = avatarUrl
        )
    }

    companion object {
        const val TABLE_NAME = "user"
    }
}

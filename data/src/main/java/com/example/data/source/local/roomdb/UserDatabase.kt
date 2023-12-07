package com.example.data.source.local.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.source.local.roomdb.converter.UserResponseEntityConverter
import com.example.data.source.local.roomdb.dao.FavouriteDao
import com.example.data.source.local.roomdb.dao.UserDao
import com.example.data.source.local.roomdb.entity.UserEntity
import com.example.data.source.local.roomdb.entity.FavouriteEntity

@Database(
    entities = [UserEntity::class, FavouriteEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(UserResponseEntityConverter::class)
abstract class UserDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getFavouriteDao(): FavouriteDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
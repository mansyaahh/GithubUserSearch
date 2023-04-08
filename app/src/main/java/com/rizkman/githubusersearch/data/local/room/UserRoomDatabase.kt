package com.rizkman.githubusersearch.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rizkman.githubusersearch.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UserRoomDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object{
        @Volatile
        private var INSTANCE: UserRoomDatabase? = null

        fun getInstance(context: Context): UserRoomDatabase =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    UserRoomDatabase::class.java,"user_database"
                ).fallbackToDestructiveMigration().build()
            }
    }
}
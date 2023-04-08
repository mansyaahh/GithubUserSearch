package com.rizkman.githubusersearch.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*

import com.rizkman.githubusersearch.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getUsers(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM user where login = :username")
    fun getUser(username: String): LiveData<UserEntity>

    @Query("SELECT * FROM user where favorited = 1")
    fun getFavoritedUser(): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUsers(user: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("DELETE FROM user WHERE favorited = 0")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM user WHERE login = :username AND favorited = 1)")
    suspend fun isUserFavorited(username: String): Boolean
}
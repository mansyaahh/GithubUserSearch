package com.rizkman.githubusersearch.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*

import com.rizkman.githubusersearch.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: UserEntity)
    @Update
    fun update(user: UserEntity)
    @Delete
    fun delete(user: UserEntity)
    @Query("SELECT * from note ORDER BY username ASC")
    fun getAllFavorite(): LiveData<List<UserEntity>>
}
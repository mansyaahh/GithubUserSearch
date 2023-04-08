package com.rizkman.githubusersearch.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.rizkman.githubusersearch.response.UserDetailResponse
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user")
@Parcelize
data class UserEntity(
    @PrimaryKey(autoGenerate = false)

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("followers")
    val followers: Int? = 0,

    @field:SerializedName("following")
    val following: Int? = 0,

    @field:SerializedName("name")
    val name: String? = "",

    @field:ColumnInfo(name = "favorited")
    var isFavorited: Boolean

    ): Parcelable


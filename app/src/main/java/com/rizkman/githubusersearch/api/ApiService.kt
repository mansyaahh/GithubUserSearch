package com.rizkman.githubusersearch.api

import com.rizkman.githubusersearch.data.local.entity.UserEntity
import com.rizkman.githubusersearch.response.ItemsItem
import com.rizkman.githubusersearch.response.SearchResponse
import com.rizkman.githubusersearch.response.UserDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<UserEntity>

    @GET("search/users")
    suspend fun getUser(@Query("q") query: String): SearchResponse

    @GET("users/{username}")
    suspend fun getUserDetail(@Path("username") username: String): UserEntity

    @GET("users/{username}/followers")
    suspend fun getFollowers(@Path("username") username: String): List<UserEntity>

    @GET("users/{username}/following")
    suspend fun getFollowings(@Path("username") username: String): List<UserEntity>
}
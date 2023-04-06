package com.rizkman.githubusersearch.api

import com.rizkman.githubusersearch.response.ItemsItem
import com.rizkman.githubusersearch.response.SearchResponse
import com.rizkman.githubusersearch.response.UserDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUser (@Query("q") query: String): Call<SearchResponse>

    @GET("users/{username}")
    fun getUserDetail(@Path("username") username: String): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowings(@Path("username") username: String): Call<List<ItemsItem>>
}
package com.rizkman.githubusersearch.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizkman.githubusersearch.api.ApiConfig
import com.rizkman.githubusersearch.data.UserRepository
import com.rizkman.githubusersearch.data.local.entity.UserEntity
import com.rizkman.githubusersearch.response.UserDetailResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getUserDetail(username: String) = userRepository.getUserDetail(username)

    fun getFollowers(username: String) = userRepository.getFollowers(username)

    fun getFollowings(username: String) = userRepository.getFollowings(username)

    fun addToFavorite(username: UserEntity){
        viewModelScope.launch {
            userRepository.setFavoritedUsers(username,true)
        }
    }

    fun removeFromFavorite(username: UserEntity){
        viewModelScope.launch {
            userRepository.setFavoritedUsers(username,false)
        }
    }

    companion object{
        private const val TAG = "UserDetailViewModel"
    }

}
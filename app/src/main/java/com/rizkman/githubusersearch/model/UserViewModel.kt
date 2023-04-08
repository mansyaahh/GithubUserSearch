package com.rizkman.githubusersearch.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizkman.githubusersearch.api.ApiConfig
import com.rizkman.githubusersearch.data.UserRepository
import com.rizkman.githubusersearch.response.ItemsItem
import com.rizkman.githubusersearch.response.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    init {
        findUser("arif")
    }

    fun getUsers() = userRepository.getUsers()
    fun findUser(query: String) = userRepository.findUser(query)


    companion object{
        private const val TAG = "UserViewModel"
    }


}
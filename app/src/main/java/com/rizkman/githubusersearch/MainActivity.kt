package com.rizkman.githubusersearch

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizkman.githubusersearch.adapter.UserAdapter
import com.rizkman.githubusersearch.data.Results
import com.rizkman.githubusersearch.data.local.entity.UserEntity
import com.rizkman.githubusersearch.databinding.ActivityMainBinding
import com.rizkman.githubusersearch.model.UserViewModel

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var userAdapter: UserAdapter
    private val TAG = "MainActivity"

    private val userViewModel by viewModels<UserViewModel>(){
        ViewModelFactory.getDatabase(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUserListData()

        userAdapter.setOnItemClickListener { UserEntity ->
            val intent = Intent(this@MainActivity,UserDetailActivity::class.java)
            intent.putExtra(UserDetailActivity.EXTRA_DATA, UserEntity.login)
            startActivity(intent)
        }

        userViewModel.getUsers().observe(this){usernameList ->
            if (usernameList != null){
                when (usernameList){
                    is Results.Loading -> {
                        showLoading(true)
                    }
                    is Results.Success -> {
                        showLoading(false)
                        val users = usernameList.data
                        userAdapter.differ.submitList(users)
                    }
                    is Results.Error -> {
                        showLoading(false)
                        Log.e(TAG,usernameList.error)
                        Toast.makeText(applicationContext,"Error",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu,menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                userViewModel.findUser(query).observe(this@MainActivity){ result ->
                    if (result != null){
                        when (result) {
                            is Results.Loading -> {
                                showLoading(true)
                            }
                            is Results.Success -> {
                                showLoading(false)
                                userAdapter.differ.submitList(result.data)
                            }
                            is Results.Error -> {
                                showLoading(false)
                                Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                userViewModel.findUser(newText).observe(this@MainActivity){newText ->
                        when (newText){
                            is Results.Loading -> {
                                showLoading(true)
                            }
                            is Results.Success -> {
                                showLoading(false)
                                userAdapter.differ.submitList(newText.data)
                            }
                            is Results.Error -> {
                                showLoading(false)
                            }
                        }
                    }
                return false
            }
        })
        return true
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setUserListData() {
        userAdapter = UserAdapter()
        binding.rvUsers.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }


    }
}
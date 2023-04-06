package com.rizkman.githubusersearch

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizkman.githubusersearch.adapter.UserAdapter
import com.rizkman.githubusersearch.databinding.ActivityMainBinding
import com.rizkman.githubusersearch.model.UserViewModel
import com.rizkman.githubusersearch.response.ItemsItem

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager

        userViewModel.userList.observe(this){usernameList ->
            setUserListData(usernameList)
        }

        userViewModel.isLoading.observe(this){
            showLoading(it)
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
                userViewModel.findUser(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                userViewModel.findUser(newText)
                return true
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

    private fun setUserListData(userList: List<ItemsItem>) {
        val adapter = UserAdapter(userList)
        binding.rvUsers.adapter = adapter

        adapter.setOnItemCallback(object : UserAdapter.OnItemClickCallBack{
            override fun onItemClicked(data: ItemsItem) {
                val detailIntent =Intent(this@MainActivity,UserDetailActivity::class.java)
                detailIntent.putExtra(UserDetailActivity.EXTRA_DATA,data.login)
                startActivity(detailIntent)
            }
        })
    }
}
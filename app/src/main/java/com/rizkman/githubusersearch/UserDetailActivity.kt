package com.rizkman.githubusersearch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rizkman.githubusersearch.adapter.SectionPagerAdapter
import com.rizkman.githubusersearch.data.Results
import com.rizkman.githubusersearch.data.local.entity.UserEntity
import com.rizkman.githubusersearch.databinding.ActivityUserDetailBinding
import com.rizkman.githubusersearch.model.UserDetailViewModel
import com.rizkman.githubusersearch.response.UserDetailResponse

class UserDetailActivity : AppCompatActivity() {

    private var _binding: ActivityUserDetailBinding? = null
    private val binding get() = _binding!!

    private val TAG = "UserDetailActivity"

    private val userDetailViewModel by viewModels<UserDetailViewModel> {
        ViewModelFactory.getDatabase(application)
    }

    companion object{
        const val EXTRA_DATA = "extra_data"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_DATA)

        if (username != null) {
            supportActionBar?.title = username
            userDetailViewModel.getUserDetail(username).observe(this){ usernameData ->
                if (usernameData != null){
                    when (usernameData){
                        is Results.Loading -> {
                            showLoading(true)
                        }
                        is Results.Success -> {
                            showLoading(false)
                            setUsernameData(usernameData.data)
                        }
                        is Results.Error -> {
                            showLoading(false)
                            Log.e(TAG,usernameData.error)
                            Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        val sectionPagerAdapter = SectionPagerAdapter(this)
        sectionPagerAdapter.username = username.toString()
        val viewPager : ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager){tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()


    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@UserDetailActivity, MainActivity::class.java)
        startActivity(intent)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }

    }

    private fun setUsernameData(usernameData: UserEntity) {
        binding.tvName.text = usernameData.name
        binding.tvUsername.text = usernameData.login
        binding.tvFollowers.text = String.format(getString(R.string.followers),usernameData.followers)
        binding.tvFollowing.text = String.format(getString(R.string.following),usernameData.following)
        Glide.with(this)
            .load(usernameData.avatarUrl)
            .into(binding.civDetailProfile)
        if (usernameData.isFavorited){
            binding.fabAdd.setImageDrawable(ContextCompat.getDrawable(binding.fabAdd.context, R.drawable.ic_fav))
            binding.fabAdd.setOnClickListener {
                userDetailViewModel.removeFromFavorite(usernameData)
            }
        }else{
            binding.fabAdd.setImageDrawable(ContextCompat.getDrawable(binding.fabAdd.context, R.drawable.ic_fav_border))
            binding.fabAdd.setOnClickListener{
                userDetailViewModel.addToFavorite(usernameData)
            }
        }


    }
}
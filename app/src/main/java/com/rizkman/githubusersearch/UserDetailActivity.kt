package com.rizkman.githubusersearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rizkman.githubusersearch.adapter.SectionPagerAdapter
import com.rizkman.githubusersearch.databinding.ActivityUserDetailBinding
import com.rizkman.githubusersearch.model.UserDetailViewModel
import com.rizkman.githubusersearch.response.UserDetailResponse

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private val userDetailViewModel by viewModels<UserDetailViewModel>()

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
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_DATA)

        if (username != null) {
            supportActionBar?.title = username
            userDetailViewModel.getUserDetail(username)
            userDetailViewModel.userDetail.observe(this){ usernameData ->
                setUsernameData(usernameData)
            }
            userDetailViewModel.isLoading.observe(this){
                showLoading(it)
            }
        }

        val sectionPagerAdapter = SectionPagerAdapter(this)
        sectionPagerAdapter.username = username.toString()
        val viewPager : ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager){tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }

    }

    private fun setUsernameData(usernameData: UserDetailResponse) {
        binding.tvName.text = usernameData.name
        binding.tvUsername.text = usernameData.login
        binding.tvFollowers.text = String.format(getString(R.string.followers),usernameData.followers)
        binding.tvFollowing.text = String.format(getString(R.string.following),usernameData.following)
        Glide.with(this)
            .load(usernameData.avatarUrl)
            .into(binding.civDetailProfile)

    }
}
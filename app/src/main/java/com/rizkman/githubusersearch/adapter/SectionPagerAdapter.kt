package com.rizkman.githubusersearch.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rizkman.githubusersearch.fragment.DetailFragment

class SectionPagerAdapter(activity : AppCompatActivity) : FragmentStateAdapter(activity) {

    var username : String = ""

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = DetailFragment()
        fragment.arguments = Bundle().apply {
            putInt(DetailFragment.POSITION, position + 1)
            putString(DetailFragment.USERNAME, username)
        }
        return fragment
    }
}
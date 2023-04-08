package com.rizkman.githubusersearch.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizkman.githubusersearch.UserDetailActivity
import com.rizkman.githubusersearch.ViewModelFactory
import com.rizkman.githubusersearch.adapter.UserAdapter
import com.rizkman.githubusersearch.data.Results
import com.rizkman.githubusersearch.data.local.entity.UserEntity
import com.rizkman.githubusersearch.databinding.FragmentDetailBinding
import com.rizkman.githubusersearch.model.UserDetailViewModel
import com.rizkman.githubusersearch.model.UserViewModel
import com.rizkman.githubusersearch.response.ItemsItem


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val userDetailViewModel by viewModels<UserDetailViewModel> {
        ViewModelFactory.getDatabase(requireActivity())
    }

    private lateinit var userAdapter: UserAdapter

    private val TAG = "F"

    companion object{
        const val POSITION = "position"
        const val USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = arguments?.getInt(POSITION)
        val username = arguments?.getString(USERNAME)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvDetail.layoutManager = layoutManager

        if (username != null){
            if (position == 1){
                userDetailViewModel.getFollowers(username).observe(viewLifecycleOwner){ followers ->
                    if (followers != null){
                        when (followers){
                            is Results.Loading -> {
                                showLoading(true)
                            }
                            is Results.Success -> {
                                showLoading(false)
                                userAdapter.differ.submitList(followers.data)
                            }
                            is Results.Error -> {
                                showLoading(false)
                                Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }else{
                userDetailViewModel.getFollowings(username).observe(viewLifecycleOwner){ followings ->
                    if (followings != null){
                        when (followings){
                            is Results.Loading -> {
                                showLoading(true)
                            }
                            is Results.Success -> {
                                showLoading(false)
                                userAdapter.differ.submitList(followings.data)
                            }
                            is Results.Error -> {
                                showLoading(false)
                                Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                }
            }
        }
    }


    private fun setFollowings(followings: List<UserEntity>) {
        userAdapter = UserAdapter()
        binding.rvDetail.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        userAdapter.setOnItemClickListener {
            val intent = Intent(requireActivity(), UserDetailActivity::class.java)
            intent.putExtra(UserDetailActivity.EXTRA_DATA,it.login)
        }
    }

    private fun setFollowers(followers: List<UserEntity>) {
        userAdapter = UserAdapter()
        binding.rvDetail.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        userAdapter.setOnItemClickListener {
            val intent = Intent(requireActivity(), UserDetailActivity::class.java)
            intent.putExtra(UserDetailActivity.EXTRA_DATA,it.login)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

}
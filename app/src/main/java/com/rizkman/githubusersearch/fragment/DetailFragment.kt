package com.rizkman.githubusersearch.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizkman.githubusersearch.adapter.UserAdapter
import com.rizkman.githubusersearch.databinding.FragmentDetailBinding
import com.rizkman.githubusersearch.model.UserViewModel
import com.rizkman.githubusersearch.response.ItemsItem


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val userViewModel by viewModels<UserViewModel>()

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

        if (username != null) {
            userViewModel.getFollowers(username)
            userViewModel.getFollowings(username)
        }
        userViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        if (position == 1){
            userViewModel.followers.observe(viewLifecycleOwner){ followers ->
                setFollowers(followers)
            }
        }else{
            userViewModel.followings.observe(viewLifecycleOwner){ followings ->
                setFollowings(followings)
            }
        }
    }

    private fun setFollowings(followings: List<ItemsItem>) {
        val adapter = UserAdapter(followings)
        binding.rvDetail.adapter = adapter

        adapter.setOnItemCallback(object : UserAdapter.OnItemClickCallBack{
            override fun onItemClicked(data: ItemsItem) {

            }

        })
    }

    private fun setFollowers(followers: List<ItemsItem>) {
        val adapter = UserAdapter(followers)
        binding.rvDetail.adapter = adapter

        adapter.setOnItemCallback(object : UserAdapter.OnItemClickCallBack{
            override fun onItemClicked(data: ItemsItem) {

            }

        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

}
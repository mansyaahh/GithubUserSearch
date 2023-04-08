package com.rizkman.githubusersearch.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rizkman.githubusersearch.R
import com.rizkman.githubusersearch.data.local.entity.UserEntity
import com.rizkman.githubusersearch.databinding.ItemRowUserBinding
import com.rizkman.githubusersearch.response.ItemsItem
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter: RecyclerView.Adapter<UserAdapter.MyViewHolder>(){
    inner class MyViewHolder(private val binding: ItemRowUserBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user: UserEntity){
            binding.tvItemUsername.text = user.login
            Glide.with(itemView)
                .load(user.avatarUrl)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(binding.civProfile)

            itemView.setOnClickListener { onItemClickListener?.let{it(user)} }
        }
    }
    private var onItemClickListener: ((UserEntity) -> Unit)? = null
    fun setOnItemClickListener(listener: (UserEntity) -> Unit){
        onItemClickListener = listener
    }

    private val diffCallback = object : DiffUtil.ItemCallback<UserEntity>(){
        override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
            return oldItem.login == newItem.login
        }

        override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }


}
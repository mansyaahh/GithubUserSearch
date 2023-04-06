package com.rizkman.githubusersearch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizkman.githubusersearch.R
import com.rizkman.githubusersearch.response.ItemsItem
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(private val userList: List<ItemsItem>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallBack

    fun setOnItemCallback (onItemClickCallBack: OnItemClickCallBack){
        this.onItemClickCallback = onItemClickCallBack
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rvLayout : CardView = view.findViewById(R.id.card_view)
        val tvItemUsername : TextView = view.findViewById(R.id.tv_item_username)
        val ivUserProfile : CircleImageView = view.findViewById(R.id.civ_profile)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_user,viewGroup,false))


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val user = userList[position].login
        val pictureUrl = userList[position].avatarUrl

        if (position % 2 == 0 ){
            viewHolder.rvLayout.setCardBackgroundColor(ContextCompat.getColor(
                viewHolder.itemView.context, R.color.dark_purple
            ))
        }else{
            viewHolder.rvLayout.setCardBackgroundColor(ContextCompat.getColor(
                viewHolder.itemView.context, R.color.light_purple
           ))
        }

        viewHolder.tvItemUsername.text = user
        Glide.with(viewHolder.itemView.context)
            .load(pictureUrl)
            .into(viewHolder.ivUserProfile)

        viewHolder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(userList[viewHolder.adapterPosition]) }
    }

    override fun getItemCount(): Int = userList.size

    interface OnItemClickCallBack{
        fun onItemClicked (data: ItemsItem)
    }
}
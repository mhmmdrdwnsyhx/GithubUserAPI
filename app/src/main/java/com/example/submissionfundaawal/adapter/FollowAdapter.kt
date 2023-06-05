package com.example.submissionfundaawal.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import com.bumptech.glide.Glide
import android.view.LayoutInflater
import com.example.submissionfundaawal.R
import androidx.recyclerview.widget.RecyclerView
import com.example.submissionfundaawal.model.Follows

class FollowAdapter(private val listUser: List<Follows>) :
    RecyclerView.Adapter<FollowAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.layout_user_list, viewGroup, false)
        )

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItemUser: TextView = view.findViewById(R.id.tv_item_user)
        val tvImg: ImageView = itemView.findViewById(R.id.img_item_photo)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val imgUrl = listUser[position].avatarUrl
        val login = listUser[position].login
        viewHolder.tvItemUser.text = login
        Glide.with(viewHolder.itemView.context)
            .load(imgUrl)
            .into(viewHolder.tvImg)
    }

    override fun getItemCount() = listUser.size
}
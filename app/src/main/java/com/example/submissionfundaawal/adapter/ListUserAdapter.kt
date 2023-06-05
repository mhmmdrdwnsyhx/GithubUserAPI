package com.example.submissionfundaawal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionfundaawal.R
import com.example.submissionfundaawal.model.UserData

class ListUserAdapter(private val listUser: ArrayList<UserData>) :
    RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserData)
    }

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
        val imgUrl = listUser[position].imgUrl
        val login = listUser[position].login
        viewHolder.tvItemUser.text = login
        Glide.with(viewHolder.itemView.context)
            .load(imgUrl)
            .into(viewHolder.tvImg)

        viewHolder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser[viewHolder.adapterPosition]) }
    }

    override fun getItemCount() = listUser.size
}
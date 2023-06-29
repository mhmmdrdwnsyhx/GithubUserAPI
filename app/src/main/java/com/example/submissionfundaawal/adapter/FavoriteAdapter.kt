package com.example.submissionfundaawal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionfundaawal.databinding.LayoutUserListBinding
import com.example.submissionfundaawal.detail.favorite.FavEntity

class FavoriteAdapter(private val listUser: List<FavEntity>) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteAdapterHolder>() {
        interface OnItemClickCallback {
            fun onItemClicked(data: FavEntity)
        }

    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class FavoriteAdapterHolder (val binding: LayoutUserListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): FavoriteAdapterHolder {
        val binding = LayoutUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteAdapterHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FavoriteAdapterHolder, position: Int)
    {
        val (_, login, avatar) = listUser[position]
        with(holder.binding) {
            Glide.with(holder.itemView.context).load(avatar).into(imgItemPhoto)
            tvItemUser.text = login
            holder.itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(listUser[holder.adapterPosition])
            }
        }
    } override fun getItemCount() = listUser.size
}
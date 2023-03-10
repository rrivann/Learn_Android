package com.dicoding.mygithubusersubmission.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mygithubusersubmission.databinding.ItemUserBinding
import com.dicoding.mygithubusersubmission.repository.response.ListFollowerUserResponseData

class FollowAdapter(
    private val listFollow: List<ListFollowerUserResponseData>,
    private val onClick: (ListFollowerUserResponseData) -> Unit
) :
    RecyclerView.Adapter<FollowAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
        ListViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.binding.tvItemUsername.text = listFollow[position].login
        Glide.with(holder.itemView.context).load(listFollow[position].avatarUrl)
            .into(holder.binding.imgItemUser)
        holder.click(listFollow[position])
    }

    override fun getItemCount(): Int = listFollow.size

    inner class ListViewHolder(var binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun click(detailUserResponse: ListFollowerUserResponseData) {
            itemView.setOnClickListener { onClick(detailUserResponse) }
        }
    }

}


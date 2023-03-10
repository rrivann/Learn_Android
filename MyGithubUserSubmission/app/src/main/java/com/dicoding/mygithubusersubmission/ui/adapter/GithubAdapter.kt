package com.dicoding.mygithubusersubmission.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mygithubusersubmission.databinding.ItemUserBinding
import com.dicoding.mygithubusersubmission.repository.response.GithubUserData

class GithubAdapter(
    private val listGithub: List<GithubUserData>,
    private val onClick: (GithubUserData) -> Unit
) :
    RecyclerView.Adapter<GithubAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
        ListViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        holder.binding.tvItemUsername.text = listGithub[position].login
        Glide.with(holder.itemView.context).load(listGithub[position].avatarUrl)
            .into(holder.binding.imgItemUser)
        holder.click(listGithub[position])
    }

    override fun getItemCount(): Int = listGithub.size

    inner class ListViewHolder(var binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun click(detailUserResponse: GithubUserData) {
            itemView.setOnClickListener { onClick(detailUserResponse) }
        }
    }

}


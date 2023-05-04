package com.dicoding.storyappsubmission.ui.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.storyappsubmission.data.local.entity.StoryEntity
import com.dicoding.storyappsubmission.databinding.StoryItemBinding
import com.dicoding.storyappsubmission.ui.detailStory.DetailStoryActivity
import com.dicoding.storyappsubmission.ui.detailStory.DetailStoryActivity.Companion.EXTRA_DETAIL
import com.dicoding.storyappsubmission.utils.setImageFromUrl


@ExperimentalPagingApi
class StoryListAdapter :
    PagingDataAdapter<StoryEntity, StoryListAdapter.ListViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
        ListViewHolder(
            StoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val story = getItem(position)
        if (story != null) holder.bind(holder.itemView.context, story)
    }

    class ListViewHolder(
        val binding: StoryItemBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, story: StoryEntity) {
            binding.apply {
                tvStoryUsername.text = story.name
                ivStoryImage.setImageFromUrl(
                    context,
                    story.photoUrl
                )

                root.setOnClickListener {
                    Intent(context, DetailStoryActivity::class.java).also { intent ->
                        intent.putExtra(EXTRA_DETAIL, story)
                        context.startActivity(intent)
                    }
                }
            }
        }
    }


    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<StoryEntity>() {
            override fun areItemsTheSame(oldItem: StoryEntity, newItem: StoryEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: StoryEntity, newItem: StoryEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}
package com.dicoding.storyappsubmission.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.storyappsubmission.database.local.entity.StoryEntity
import com.dicoding.storyappsubmission.databinding.StoryItemBinding
import com.dicoding.storyappsubmission.ui.detailStory.DetailStoryActivity
import com.dicoding.storyappsubmission.ui.detailStory.DetailStoryActivity.Companion.EXTRA_DETAIL
import com.dicoding.storyappsubmission.utils.setImageFromUrl

class StoryListAdapter(
    private val listStory: List<StoryEntity>,
) :
    RecyclerView.Adapter<StoryListAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
        ListViewHolder(
            StoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = listStory.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) =
        holder.bind(holder.itemView.context, listStory[position])

    inner class ListViewHolder(
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
}
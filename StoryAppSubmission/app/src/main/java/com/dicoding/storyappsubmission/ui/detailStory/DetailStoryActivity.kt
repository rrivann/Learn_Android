package com.dicoding.storyappsubmission.ui.detailStory

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dicoding.storyappsubmission.R
import com.dicoding.storyappsubmission.data.local.entity.StoryEntity
import com.dicoding.storyappsubmission.data.remote.response.DetailStory
import com.dicoding.storyappsubmission.databinding.ActivityDetailStoryBinding
import com.dicoding.storyappsubmission.utils.setLocalDateFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.dicoding.storyappsubmission.utils.Result
import com.dicoding.storyappsubmission.utils.showLoading
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
@ExperimentalPagingApi
class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.action_detail)

        showDetailStory()
    }

    private fun showDetailStory() {
        val story = intent.getParcelableExtra<StoryEntity>(EXTRA_DETAIL)
        if (story != null) {
            lifecycleScope.launch {
                detailViewModel.getAuthToken().collect { token ->
                    if (token != null)
                        detailViewModel.getDetailStory(token, story.id)
                            .collect { result ->
                                when (result) {
                                    is Result.Loading -> {
                                        showLoading(true, binding.progressBar)
                                    }

                                    is Result.Success -> {
                                        showLoading(false, binding.progressBar)
                                        parseStoryData(result.data.story)
                                    }

                                    is Result.Error -> {
                                        showLoading(false, binding.progressBar)
                                        Toast.makeText(
                                            this@DetailStoryActivity,
                                            result.error,
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                    }
                                }
                            }
                }

            }

        }
    }

    private fun parseStoryData(story: DetailStory?) {
        if (story != null) {
            binding.apply {
                tvStoryUsername.text = story.name
                tvStoryDescription.text =
                    String.format(getString(R.string.description), story.description)
                tvStoryDate.setLocalDateFormat(story.createdAt)
                Glide
                    .with(this@DetailStoryActivity)
                    .load(story.photoUrl)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            supportStartPostponedEnterTransition()
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            supportStartPostponedEnterTransition()
                            return false
                        }
                    })
                    .placeholder(R.drawable.image_loading_placeholder)
                    .error(R.drawable.image_load_error)
                    .into(ivStoryImage)
            }
        }
    }

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }
}
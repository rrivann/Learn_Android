package com.dicoding.mygithubusersubmission.ui.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.bumptech.glide.Glide
import com.dicoding.mygithubusersubmission.R
import com.dicoding.mygithubusersubmission.database.local.entity.FavoriteUserEntity
import com.dicoding.mygithubusersubmission.databinding.ActivityDetailBinding
import com.dicoding.mygithubusersubmission.helper.SettingPreferences
import com.dicoding.mygithubusersubmission.helper.ViewModelFactory
import com.dicoding.mygithubusersubmission.repository.response.DetailUserResponse
import com.dicoding.mygithubusersubmission.ui.adapter.FollowTabAdapter
import com.dicoding.mygithubusersubmission.ui.viewmodels.DetailViewModel
import com.dicoding.mygithubusersubmission.ui.viewmodels.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val detailViewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.title_action_bar_detail)

        val username = intent.getStringExtra(USERNAME)
        val avatarUrl = intent.getStringExtra(AVATARURL)

        mainViewModel.getDetailUser(username)
        mainViewModel.isLoading.observe(this) { showLoading(it) }
        mainViewModel.detailUser.observe(this) { setDetailUser(it) }

        val followTabAdapter = FollowTabAdapter(this)
        followTabAdapter.username = username
        binding.viewPager.adapter = followTabAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        val fabAdd = binding.fabAdd

        detailViewModel.getBookmarkedNews(
            FavoriteUserEntity(username!!)
        ).observe(this) { favoriteUser ->
            if (favoriteUser != null) {
                fabAdd.setOnClickListener {
                    detailViewModel.deleteFavorite(
                        FavoriteUserEntity(
                            username
                        )
                    )
                }

                fabAdd.setImageDrawable(
                    ContextCompat.getDrawable(
                        fabAdd.context,
                        R.drawable.ic_favorite_white_24
                    )
                )

            } else {
                fabAdd.setOnClickListener {
                    detailViewModel.insertFavorite(
                        FavoriteUserEntity(
                            username,
                            avatarUrl
                        )
                    )
                }
                fabAdd.setImageDrawable(
                    ContextCompat.getDrawable(
                        fabAdd.context,
                        R.drawable.ic_favorite_border_white_24
                    )
                )
            }
        }

    }

    private fun setDetailUser(detailUser: DetailUserResponse?) {
        binding.tvDetailFullName.text = detailUser?.name ?: "No Full Name"
        binding.tvDetailUsername.text = detailUser?.login
        binding.tvDetailFollowers.text =
            String.format(getString(R.string.followers), detailUser?.followers)
        binding.tvDetailFollowing.text =
            String.format(getString(R.string.following), detailUser?.following)
        Glide.with(this).load(detailUser?.avatarUrl).into(binding.imgDetailUser)

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val USERNAME = "username_detail"
        const val AVATARURL = "avatarUrl_detail"
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val ARG_USERNAME_TAB = "username_tab"
        const val ARG_SECTION_NUMBER = "section_number"
    }
}
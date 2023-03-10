package com.dicoding.mygithubusersubmission.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.dicoding.mygithubusersubmission.R
import com.dicoding.mygithubusersubmission.databinding.ActivityDetailBinding
import com.dicoding.mygithubusersubmission.repository.response.DetailUserResponse
import com.dicoding.mygithubusersubmission.ui.adapter.FollowTabAdapter
import com.dicoding.mygithubusersubmission.ui.viewmodels.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val mainViewModel: MainViewModel by viewModels()

    companion object {
        const val USERNAME = "username_detail"
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val ARG_USERNAME_TAB = "username_tab"
        const val ARG_SECTION_NUMBER = "section_number"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Detail Github User"
        supportActionBar?.elevation = 0f

        val username = intent.getStringExtra(USERNAME)
        mainViewModel.getDetailUser(username!!)
        mainViewModel.isLoading.observe(this) { showLoading(it) }
        mainViewModel.detailUser.observe(this) { setDetailUser(it) }

        val followTabAdapter = FollowTabAdapter(this)
        followTabAdapter.username = username
        binding.viewPager.adapter = followTabAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
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
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

}
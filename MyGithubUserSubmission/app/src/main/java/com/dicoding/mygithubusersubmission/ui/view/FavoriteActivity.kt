package com.dicoding.mygithubusersubmission.ui.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mygithubusersubmission.R
import com.dicoding.mygithubusersubmission.databinding.ActivityFavoriteBinding
import com.dicoding.mygithubusersubmission.helper.SettingPreferences
import com.dicoding.mygithubusersubmission.helper.ViewModelFactory
import com.dicoding.mygithubusersubmission.repository.response.GithubUserData
import com.dicoding.mygithubusersubmission.ui.adapter.GithubAdapter
import com.dicoding.mygithubusersubmission.ui.viewmodels.DetailViewModel

class FavoriteActivity : AppCompatActivity() {

    private val detailViewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(application)
    }

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.title_action_bar_home)

        detailViewModel.getAllFavorite().observe(this) { users ->

            val items = arrayListOf<GithubUserData>()

            users.map {
                val item = GithubUserData(login = it.username, avatarUrl = it.avatarUrl!!)
                items.add(item)
            }

            val layoutManager = LinearLayoutManager(this)
            binding.rvUser.layoutManager = layoutManager
            val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
            binding.rvUser.addItemDecoration(itemDecoration)

            val adapter = GithubAdapter(items) {
                val moveIntent = Intent(this, DetailActivity::class.java)
                moveIntent.putExtra(DetailActivity.USERNAME, it.login)
                moveIntent.putExtra(DetailActivity.AVATARURL, it.avatarUrl)
                startActivity(moveIntent)
            }

            binding.rvUser.adapter = adapter
        }
    }
}
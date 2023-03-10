package com.dicoding.mygithubusersubmission.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.mygithubusersubmission.ui.view.DetailActivity
import com.dicoding.mygithubusersubmission.ui.view.FollowFragment

class FollowTabAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username: String = ""

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        fragment.arguments = Bundle().apply {
            putInt(DetailActivity.ARG_SECTION_NUMBER, position + 1)
            putString(DetailActivity.ARG_USERNAME_TAB, username)
        }
        return fragment

    }
}
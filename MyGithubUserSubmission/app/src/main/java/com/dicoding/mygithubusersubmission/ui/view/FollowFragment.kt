package com.dicoding.mygithubusersubmission.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mygithubusersubmission.databinding.FragmentFollowBinding
import com.dicoding.mygithubusersubmission.repository.response.ListFollowerUserResponseData
import com.dicoding.mygithubusersubmission.ui.adapter.FollowAdapter
import com.dicoding.mygithubusersubmission.ui.viewmodels.FollowViewModel

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding
    private val followViewModel: FollowViewModel by viewModels()
    private var username: String? = null
    private var position: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(DetailActivity.ARG_SECTION_NUMBER)
            username = it.getString(DetailActivity.ARG_USERNAME_TAB)
        }

        followViewModel.isLoadingFollow.observe(viewLifecycleOwner) { showLoadingFollow(it) }

        if (position == 1) {
            followViewModel.getFollowersUser(username)
            followViewModel.listFollowers.observe(viewLifecycleOwner) { showFollowUser(it) }
        } else {
            followViewModel.getFollowingUser(username)
            followViewModel.listFollowing.observe(viewLifecycleOwner) { showFollowUser(it) }
        }

    }


    private fun showFollowUser(listFollow: List<ListFollowerUserResponseData>?) {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding?.rvFollow?.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding?.rvFollow?.addItemDecoration(itemDecoration)

        val adapter = listFollow?.let { list ->
            FollowAdapter(list) {
                val moveIntent = Intent(activity, DetailActivity::class.java)
                moveIntent.putExtra(DetailActivity.USERNAME, it.login)
                startActivity(moveIntent)
            }
        }
        binding?.rvFollow?.adapter = adapter
    }

    private fun showLoadingFollow(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}
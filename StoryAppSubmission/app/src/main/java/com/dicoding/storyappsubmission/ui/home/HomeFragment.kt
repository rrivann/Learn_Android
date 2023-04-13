package com.dicoding.storyappsubmission.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.storyappsubmission.adapter.StoryListAdapter
import com.dicoding.storyappsubmission.database.local.entity.StoryEntity
import com.dicoding.storyappsubmission.databinding.FragmentHomeBinding
import com.dicoding.storyappsubmission.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import  com.dicoding.storyappsubmission.utils.Result
import com.dicoding.storyappsubmission.utils.showLoading

@AndroidEntryPoint
@ExperimentalPagingApi
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var token: String = ""
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        token = requireActivity().intent.getStringExtra(MainActivity.EXTRA_TOKEN) ?: ""

        setSwipeRefreshLayout()
        getAllStories()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setSwipeRefreshLayout() {
        binding.swipeRefresh.setOnRefreshListener {
            getAllStories()
        }
    }

    private fun getAllStories() {
        homeViewModel.getAllStories(token).observe(viewLifecycleOwner) { result ->
            if (result != null) when (result) {
                is Result.Loading -> {
                    showLoading(true, binding.progressBar)
                    binding.swipeRefresh.isRefreshing = true
                }
                is Result.Success -> {
                    showLoading(false, binding.progressBar)
                    binding.swipeRefresh.isRefreshing = false
                    homeViewModel.getListStoryDatabase().observe(viewLifecycleOwner) { listStory ->
                        showListStory(listStory)
                    }
                }
                is Result.Error -> {
                    binding.swipeRefresh.isRefreshing = false
                    showLoading(false, binding.progressBar)
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT)
                        .show()

                }
            }
        }
    }

    private fun showListStory(listStory: List<StoryEntity>) {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvStory.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvStory.addItemDecoration(itemDecoration)

        val adapter = StoryListAdapter(listStory)

        binding.rvStory.adapter = adapter
    }

}
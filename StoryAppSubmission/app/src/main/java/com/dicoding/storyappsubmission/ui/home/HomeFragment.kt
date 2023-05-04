package com.dicoding.storyappsubmission.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.storyappsubmission.R
import com.dicoding.storyappsubmission.data.local.entity.StoryEntity
import com.dicoding.storyappsubmission.databinding.FragmentHomeBinding
import com.dicoding.storyappsubmission.ui.home.HomeActivity.Companion.EXTRA_TOKEN
import com.dicoding.storyappsubmission.ui.main.MainActivity
import com.dicoding.storyappsubmission.ui.map.MapsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalPagingApi
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private var token: String = ""
    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var rvStory: RecyclerView
    private lateinit var listAdapter: StoryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        token = requireActivity().intent.getStringExtra(MainActivity.EXTRA_TOKEN) ?: ""

        setSwipeRefreshLayout()
        showListStory()
        getAllStories()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.maps -> {
                val intent = Intent(activity, MapsActivity::class.java)
                intent.putExtra(EXTRA_TOKEN, token)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun setSwipeRefreshLayout() {
        binding?.swipeRefresh?.setOnRefreshListener {
            getAllStories()
        }
    }

    private fun getAllStories() {
        lifecycleScope.launch {
            homeViewModel.getAllStories(token)?.collect {
                listAdapter.submitData(lifecycle, it)
            }
        }
    }

    private fun showListStory() {
        val linearLayoutManager = LinearLayoutManager(requireActivity())
        listAdapter = StoryListAdapter()

        listAdapter.addLoadStateListener { loadState ->
            binding?.swipeRefresh?.isRefreshing = loadState.source.refresh is LoadState.Loading
        }

//      SETUP ADAPTER
        try {
            val itemDecoration =
                DividerItemDecoration(requireActivity(), linearLayoutManager.orientation)
            rvStory = binding?.rvStory!!
            rvStory.apply {
                adapter = listAdapter.withLoadStateFooter(
                    footer = LoadingStateAdapter {
                        listAdapter.retry()
                    }
                )
                layoutManager = linearLayoutManager
                addItemDecoration(itemDecoration)
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

    }

}
package com.dicoding.themoviesubmission.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.core.data.Resource
import com.dicoding.core.ui.MovieAdapter
import com.dicoding.themoviesubmission.R
import com.dicoding.themoviesubmission.databinding.FragmentHomeBinding
import com.dicoding.themoviesubmission.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private val homeViewModel by viewModel<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val movieAdapter = MovieAdapter()
            movieAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DATA, selectedData)
                startActivity(intent)
            }

            homeViewModel.getAllMovie.observe(viewLifecycleOwner) {
                it.apply {
                    if (this != null) {
                        when (this) {
                            is Resource.Loading -> binding?.progressBar?.visibility = View.VISIBLE
                            is Resource.Success -> {
                                binding?.progressBar?.visibility = View.GONE
                                movieAdapter.setData(data)
                            }

                            is Resource.Error -> {
                                binding?.progressBar?.visibility = View.GONE
                                binding?.viewError?.root?.visibility = View.VISIBLE
                                binding?.viewError?.tvError?.text =
                                    message ?: getString(R.string.something_wrong)
                            }
                        }
                    }
                }
            }

            binding?.let {
                with(it.rvTourism) {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = movieAdapter
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
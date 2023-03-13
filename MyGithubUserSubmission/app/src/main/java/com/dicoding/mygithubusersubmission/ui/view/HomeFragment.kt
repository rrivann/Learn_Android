package com.dicoding.mygithubusersubmission.ui.view

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mygithubusersubmission.R
import com.dicoding.mygithubusersubmission.databinding.FragmentHomeBinding
import com.dicoding.mygithubusersubmission.repository.response.GithubUserData
import com.dicoding.mygithubusersubmission.ui.adapter.GithubAdapter
import com.dicoding.mygithubusersubmission.ui.viewmodels.MainViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.searchGithubUser("Rivan")
        mainViewModel.isLoading.observe(viewLifecycleOwner) { showLoading(it) }
        mainViewModel.githubUser.observe(viewLifecycleOwner) { showSearchUser(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = activity?.getSystemService<SearchManager>()
        val searchView = menu.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager?.getSearchableInfo(requireActivity().componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                mainViewModel.searchGithubUser(query)
                searchView.clearFocus()
                return true
            }
        })

    }

    private fun showSearchUser(githubUser: List<GithubUserData>) {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding?.rvUser?.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding?.rvUser?.addItemDecoration(itemDecoration)

        val adapter = GithubAdapter(githubUser) {
            val moveIntent = Intent(activity, DetailActivity::class.java)
            moveIntent.putExtra(DetailActivity.USERNAME, it.login)
            startActivity(moveIntent)
        }

        binding?.rvUser?.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }
}
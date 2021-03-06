package com.blogspot.soyamr.newsplusplus.presentation.news_list.news

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import com.blogspot.soyamr.newsplusplus.R
import com.blogspot.soyamr.newsplusplus.databinding.FragmentNewsListBinding
import com.blogspot.soyamr.newsplusplus.presentation.image.ImageActivity
import com.blogspot.soyamr.newsplusplus.presentation.news_list.news.adapters.news_list_adapter.NewsAdapter
import com.blogspot.soyamr.newsplusplus.presentation.news_list.news.adapters.news_load_state_adapter.NewsLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsListFragment : Fragment(R.layout.fragment_news_list) {

    private val viewModel: NewsListViewModel by viewModels()
    private val binding: FragmentNewsListBinding by viewBinding()

    private val onClick: (String) -> Unit = {
        findNavController().navigate(
            NewsListFragmentDirections.actionNewsListFragmentToWebViewFragment(
                it
            )
        )
    }

    private val adapter = NewsAdapter(onClick)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        getNews()
        setListeners()
        initSwipeRefresh()
    }

    private fun setListeners() {
        binding.retryButton.setOnClickListener { adapter.retry() }

        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(requireContext(), ImageActivity::class.java))
        }
    }

    private fun initSwipeRefresh() {
        binding.swiperefresh.setOnRefreshListener {
            adapter.refresh()
        }
    }


    private var searchJob: Job? = null

    private fun getNews() {
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.news.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private var toast: Toast? = null

    private fun initAdapter() {
        binding.list.adapter = adapter.withLoadStateHeaderAndFooter(
            header = NewsLoadStateAdapter { adapter.retry() },
            footer = NewsLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { loadState ->

            // show empty list
            val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
            showEmptyList(isListEmpty)

            if (loadState.source.append is LoadState.NotLoading &&
                loadState.source.append.endOfPaginationReached && adapter.itemCount == 100
            ) {
                showError("Loaded all data")
            }

            // Only show the list if refresh succeeds.
            binding.list.isVisible = loadState.mediator?.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            binding.swiperefresh.isRefreshing = loadState.mediator?.refresh is LoadState.Loading
            binding.progressBar.isVisible = loadState.mediator?.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails.
            binding.retryButton.isVisible = loadState.mediator?.refresh is LoadState.Error
            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                showError("\uD83D\uDE28 Wooops" + it.error.message!!)

            }
        }
    }

    private fun showError(error: String) {
        toast?.cancel()
        toast = Toast.makeText(
            requireContext(),
            error,
            Toast.LENGTH_SHORT
        )
        toast?.show()
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            binding.emptyList.visibility = View.VISIBLE
            binding.list.visibility = View.GONE
        } else {
            binding.emptyList.visibility = View.GONE
            binding.list.visibility = View.VISIBLE
        }
    }
}
package com.example.myapplication.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.RepoFragBinding
import com.example.myapplication.ui.model.RepoItemUIModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RepoFragment: BaseFragment() {

    @Inject
    lateinit var repoAdapter : TrendingRepoAdapter
    private val viewModel: RepoViewModel by viewModels()

    private lateinit var binding: RepoFragBinding

    /**
     * Variables to Support paging
     */
    private var isMoreItems = true
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RepoFragBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(root: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.swipeRefreshLayout.setOnRefreshListener(this::swipeRefresh)
        binding.recyclerView.layoutManager= LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = repoAdapter
        setUpObservers()
        binding.layoutEmpty.emptyRetry.setOnClickListener {
            fetchTrendingRepo()
        }

        binding.layoutError.errorRetry.setOnClickListener {
            fetchTrendingRepo()
        }

        fetchTrendingRepo()

        binding.recyclerView.addOnScrollListener(object: PaginationListener(binding.recyclerView.layoutManager as LinearLayoutManager){
            override fun loadMoreItems() {
                isLoading = true
                showHideLoader(isLoading)
                viewModel.getTrendingRepoList()
            }
            override fun isLastPage(): Boolean {
                return !isMoreItems
            }
            override fun isLoading(): Boolean {
                return  isLoading
            }
        })
    }

    private fun setUpObservers() {
        viewModel.spinner.observe(viewLifecycleOwner, {
            showHideLoader(it)
        })
        viewModel.repoList.observe(viewLifecycleOwner, {
            inflateData(it)
        })

        viewModel.error.observe(viewLifecycleOwner, {
            showError()
        })

        viewModel.emptyView.observe(viewLifecycleOwner, {
            showEmptyView()
        })

        viewModel.hideZeroStateView.observe(viewLifecycleOwner, {
            hideZeroStateViews()
        })

        viewModel.swipeLoader.observe(viewLifecycleOwner, {
            binding.swipeRefreshLayout.isRefreshing = false
        })

        viewModel.snackbar.observe(viewLifecycleOwner, {
            val msg: CharSequence = getString(R.string.offline_data)
            Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
        })

    }
    private fun fetchTrendingRepo(){
        viewModel.getTrendingRepoList()
    }

    private fun swipeRefresh() {
        isMoreItems = true
        isLoading = false
        repoAdapter.clearItems()
        viewModel.doRefreshData(false)
    }

    private fun inflateData(list: List<RepoItemUIModel>) {
        if(list.isNotEmpty()){
            isMoreItems = list.size >= PaginationListener.PAGE_SIZE
            isLoading = false
            showHideLoader(false)
            if(repoAdapter.itemCount>0){
                repoAdapter.addItems(list)
            }else {
                repoAdapter.setItems(list)
            }
            return
        }
    }

    private fun showHideLoader(isVisible: Boolean) {
        if (isVisible) binding.layoutLoading.progressBarLoading.visibility = View.VISIBLE
        else binding.layoutLoading.progressBarLoading.visibility = View.GONE
    }

    private fun showEmptyView() {
        binding.layoutEmpty.root.visibility= View.VISIBLE
    }

    private fun showError() {
        binding.layoutError.root.visibility = View.VISIBLE
    }

    private fun hideZeroStateViews() {
        binding.layoutEmpty.root.visibility= View.GONE
        binding.layoutError.root.visibility = View.GONE
    }


}
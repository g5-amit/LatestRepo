package com.example.myapplication.ui

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Supporting only LinearLayoutManager for now.
 * Refer: https://androidwave.com/pagination-in-recyclerview/
 */
abstract class PaginationListener(private val layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    companion object {
//        const val PAGE_START = 1
        /**
         * Set scrolling threshold here for one page
         */
        const val PAGE_SIZE = 10
    }
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        if (!isLoading() && !isLastPage()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE) {
                loadMoreItems()
            }
        }
    }
    protected abstract fun loadMoreItems()
    protected abstract fun isLastPage(): Boolean
    protected abstract fun isLoading(): Boolean
}
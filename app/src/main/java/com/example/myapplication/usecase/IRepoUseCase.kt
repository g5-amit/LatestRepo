package com.example.myapplication.usecase

import com.example.myapplication.network.util.Resource
import com.example.myapplication.ui.model.RepoItemUIModel
import com.example.myapplication.utility.Sort

interface IRepoUseCase {
    suspend fun getTrendingRepoList(sortOrder: Sort): Resource<List<RepoItemUIModel>>
    suspend fun getRefreshRepoList(sortOrder: Sort): Resource<List<RepoItemUIModel>>
}
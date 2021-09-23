package com.example.myapplication.usecase

import com.example.myapplication.data.TrendingRepoRepository
import com.example.myapplication.di.scope.IoDispatcher
import com.example.myapplication.dispatcher.CoroutineDispatcherProvider
import com.example.myapplication.dispatcher.RealCoroutineDispatcherProvider
import com.example.myapplication.network.api.response.GitHubRepo
import com.example.myapplication.network.util.Resource
import com.example.myapplication.ui.model.RepoItemUIModel
import com.example.myapplication.utility.Sort
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class RepoUseCaseImpl @Inject constructor(
    private val trendingRepository: TrendingRepoRepository,
    private val dispatcher: CoroutineDispatcherProvider
) : IRepoUseCase {

    override suspend fun getTrendingRepoList(sortOrder: Sort): Resource<List<RepoItemUIModel>> {
        return withContext(dispatcher.io) {
            when (val res = trendingRepository.getTrendingRepoList()) {
                is Resource.Success -> {
                    val uiList = res.data?.let {
                        it.map { repo ->
                            convertToUIModel(repo)
                        }
                    }
                    val sortedList = if(sortOrder == Sort.NAME){
                        uiList?.sortedBy { item ->
                            item.name
                        }
                    }else{
                        uiList?.sortedByDescending { item ->
                            item.stars
                        }
                    }
                    Resource.Success(uiList)
                }
                is Resource.Error -> Resource.Error(res.msg)
                is Resource.Loading -> Resource.Loading()
                is Resource.Offline -> Resource.Offline()
            }
        }
    }

    override suspend fun getRefreshRepoList(sortOrder: Sort): Resource<List<RepoItemUIModel>> {
        return withContext(dispatcher.io) {
            when (val res = trendingRepository.getTrendingRepoList()) {
                is Resource.Success -> {
                    val uiList = res.data?.let {
                        it.map { repo ->
                            convertToUIModel(repo)
                        }
                    }
                    val sortedList = if(sortOrder == Sort.NAME){
                        uiList?.sortedBy { item ->
                            item.name
                        }
                    }else{
                        uiList?.sortedByDescending { item ->
                            item.stars
                        }
                    }
                    Resource.Success(sortedList)
                }
                is Resource.Error -> Resource.Error(res.msg)
                is Resource.Loading -> Resource.Loading()
                is Resource.Offline -> Resource.Offline()
            }
        }
    }

    private fun convertToUIModel(trendingRepo: GitHubRepo): RepoItemUIModel {
        return RepoItemUIModel(
            trendingRepo.author,
            trendingRepo.name,
            trendingRepo.avatar,
            trendingRepo.description,
            trendingRepo.stars,
            trendingRepo.forks,
            trendingRepo.currentPeriodStars,
            trendingRepo.language,
            trendingRepo.languageColor,
            trendingRepo.url,
            trendingRepo.builtBy,
            System.currentTimeMillis()
        )
    }
}
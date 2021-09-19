package com.example.myapplication.usecase

import com.example.myapplication.data.TrendingRepoRepository
import com.example.myapplication.di.scope.IoDispatcher
import com.example.myapplication.network.api.response.GitHubRepo
import com.example.myapplication.network.util.Resource
import com.example.myapplication.ui.model.RepoItemUIModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class RepoUseCaseImpl @Inject constructor(
    private val trendingRepository: TrendingRepoRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : IRepoUseCase {

    override suspend fun getTrendingRepoList(): Resource<List<RepoItemUIModel>> {
        return withContext(dispatcher) {
            when (val res = trendingRepository.getTrendingRepoList()) {
                is Resource.Success -> {
                    val uiList = res.data?.let {
                        it.map { repo ->
                            convertToUIModel(repo)
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
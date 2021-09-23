package com.example.myapplication.ui

import androidx.lifecycle.*
import com.example.myapplication.data.TrendingRepoRepository
import com.example.myapplication.network.util.Resource
import com.example.myapplication.ui.model.RepoItemUIModel
import com.example.myapplication.usecase.RepoUseCaseImpl
import com.example.myapplication.utility.Sort
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RepoViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle,
    private val repoUseCase: RepoUseCaseImpl
): ViewModel() {

    /**
     *Default Sorting order is Stars
     */
    private var sortOrder = Sort.NAME

    /**
     * Request a snackbar to display a string.
     */
    private val _snackbar = MutableLiveData<Boolean>()
    val snackbar: LiveData<Boolean>
        get() = _snackbar

    /**
     * Show a loading spinner if true
     */
    val _spinner = MutableLiveData<Boolean>()
    val spinner: LiveData<Boolean>
        get() = _spinner

    /**
     * Hide a Pull to refresh Loader if data is refreshed
     * */
    private val _swipeLoader = MutableLiveData<Boolean>()
    val swipeLoader: LiveData<Boolean>
        get() = _swipeLoader

    /**
     * Show a Error Layout if Error occurred while fetching data
     * */
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?>
        get() = _error

    /**
     * Show a Error Layout if Error occurred while fetching data
     * */
    private val _emptyView = MutableLiveData<Boolean>()
    val emptyView: LiveData<Boolean>
        get() = _emptyView

    /**
     * Show a Error Layout if Error occurred while fetching data
     * */
    private val _hideZeroStateView = MutableLiveData<Boolean>()
    val hideZeroStateView: LiveData<Boolean>
        get() = _hideZeroStateView

    /**
     * Show Repo Listing on UI either from DB or Network
     */
    private val _repoList = MutableLiveData<List<RepoItemUIModel>>()
    val repoList: LiveData<List<RepoItemUIModel>>
        get() = _repoList

    /**
     * Logic to make UI reactive using LiveData after fetching data
     */

    fun getTrendingRepoList(){
        viewModelScope.launch {
            when(val res= repoUseCase.getTrendingRepoList(sortOrder)){
                is Resource.Success->{
                    res.data?.let{
                        _repoList.value = it
                    }
                }
                is Resource.Offline ->{
                    _emptyView.value = true
                }
                is Resource.Loading->{
                    _spinner.value = true
                }
                is Resource.Error ->{
                    _error.value = res.msg
                }
            }
        }
    }

    /**
     * Refresh Repo Data by calling Network Api
     * Once Api response is updated into DB, it will reflect on UI using Room DB reactive Flow
     * This method will be called If Data is stale, swipe refreshed, or DB data is empty
     * */
    fun doRefreshData(isEmptyViewRequired: Boolean){
        viewModelScope.launch {
            val res= repoUseCase.getRefreshRepoList(sortOrder)
            _swipeLoader.value = false
            _spinner.value = false
            when(res){
                is Resource.Success->{
                    res.data?.let{
                        _repoList.value = it
                    }
                }
                is Resource.Offline ->{
                    if(isEmptyViewRequired)
                        _emptyView.value = true
                }
                is Resource.Loading->{
                    _spinner.value = true
                }
                is Resource.Error ->{
                    _error.value = res.msg
                }
            }
        }
    }
}
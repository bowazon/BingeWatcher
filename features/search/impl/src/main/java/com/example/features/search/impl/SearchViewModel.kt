package com.example.features.search.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.tvmaze.TvMazeRemoteDataSource
import com.example.network.tvmaze.models.TvMazeShow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val api: TvMazeRemoteDataSource
) : ViewModel() {
    private val _searchState = MutableStateFlow<List<TvMazeShow>>(emptyList())
    val searchState = _searchState.asStateFlow()

    private val _query = MutableStateFlow<String>("")

    init {
        viewModelScope.launch {
            _query.collect {
                _searchState.emit(api.search(it).map { it.show })
            }
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            _query.emit(query)
        }
    }
}
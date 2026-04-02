package com.bowazon.bingewatcher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bowazon.bingewatcher.core.network.tvmaze.TvMazeRemoteDataSource
import com.bowazon.bingewatcher.core.network.tvmaze.models.TvMazeShow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val api: TvMazeRemoteDataSource
): ViewModel() {
    private val _searchState = MutableStateFlow<List<TvMazeShow>>(emptyList())
    val searchState = _searchState.asStateFlow()

    init {
        viewModelScope.launch {
            _searchState.emit(api.search("girls").map { it.show })
        }
    }
}
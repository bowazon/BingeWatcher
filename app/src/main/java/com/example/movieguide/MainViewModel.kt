package com.example.movieguide

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.tvmaze.TvMazeApi
import com.example.network.tvmaze.models.Show
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val api: TvMazeApi
): ViewModel() {
    private val _searchState = MutableStateFlow<List<Show>>(emptyList())
    val searchState = _searchState.asStateFlow()

    init {
        viewModelScope.launch {
            _searchState.emit(api.search("girls").getOrNull() ?: emptyList())
        }
    }
}
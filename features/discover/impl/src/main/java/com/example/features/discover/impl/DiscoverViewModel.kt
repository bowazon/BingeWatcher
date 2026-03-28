package com.example.features.discover.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.entities.ShowDetailed
import com.example.repository.ShowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    val repository: ShowRepository
) : ViewModel() {
    private val _searchState = MutableStateFlow<List<ShowDetailed>>(emptyList())
    val searchState = _searchState.asStateFlow()

    init {
        viewModelScope.launch {
            _searchState.emit(repository.searchShow("girls"))
        }
    }
}
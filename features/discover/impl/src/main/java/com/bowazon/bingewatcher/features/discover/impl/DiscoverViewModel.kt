package com.bowazon.bingewatcher.features.discover.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bowazon.bingewatcher.core.entities.ShowDetailed
import com.bowazon.bingewatcher.core.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    val repository: SearchRepository
) : ViewModel() {
    private val _searchState = MutableStateFlow<List<ShowDetailed>>(emptyList())
    val searchState = _searchState.asStateFlow()

    init {
        viewModelScope.launch {
            _searchState.emit(repository.searchShow("girls"))
        }
    }
}
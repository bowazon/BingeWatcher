package com.bowazon.bingewatcher.features.search.impl

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
class SearchViewModel @Inject constructor(
    val repository: SearchRepository
) : ViewModel() {
    private val _searchState = MutableStateFlow<List<ShowDetailed>>(emptyList())
    val searchState = _searchState.asStateFlow()

    private val _query = MutableStateFlow<String>("")

    init {
        viewModelScope.launch {
            _query.collect {
                // TODO SHOULD NOT SEARCH FROM START
                //try {
                    _searchState.emit(repository.searchShow(it))
                //}
            }
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            _query.emit(query)
        }
    }
}
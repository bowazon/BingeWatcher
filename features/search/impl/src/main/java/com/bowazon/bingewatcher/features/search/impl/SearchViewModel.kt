package com.bowazon.bingewatcher.features.search.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bowazon.bingewatcher.core.entities.Person
import com.bowazon.bingewatcher.core.entities.ShowDetailed
import com.bowazon.bingewatcher.core.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SearchType {
    SHOWS,
    PEOPLE
}

sealed class SearchItem {
    data class ShowItem(val data: ShowDetailed) : SearchItem()
    data class PersonItem(val data: Person) : SearchItem()
}

@HiltViewModel
class SearchViewModel @Inject constructor(
    val repository: SearchRepository
) : ViewModel() {
    private val _searchState = MutableStateFlow<List<SearchItem>>(emptyList())
    val searchState = _searchState.asStateFlow()

    private val _query = MutableStateFlow("")
    private val _searchType = MutableStateFlow(SearchType.SHOWS)

    val searchType = _searchType.asStateFlow()

    init {
        viewModelScope.launch {
            combine(_query, _searchType) { query, type ->
                query to type
            }
                .debounce(300)
                .distinctUntilChanged()
                .flatMapLatest { (query, type) ->
                    flow {
                        val result = when (type) {
                            SearchType.SHOWS -> repository.searchShow(query).map { SearchItem.ShowItem(it) }
                            SearchType.PEOPLE -> repository.searchPeople(query).map { SearchItem.PersonItem(it) }
                        }
                        emit(result)
                    }
                }
                .collect {
                    _searchState.value = it
                }
        }
    }

    fun search(query: String) {
        _query.value = query
    }

    fun setSearchType(type: SearchType) {
        _searchType.value = type
    }
}
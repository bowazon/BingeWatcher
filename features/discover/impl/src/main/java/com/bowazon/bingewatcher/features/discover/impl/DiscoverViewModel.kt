package com.bowazon.bingewatcher.features.discover.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bowazon.bingewatcher.core.entities.ShowDetailed
import com.bowazon.bingewatcher.core.repository.DiscoverRepository
import com.bowazon.bingewatcher.core.repository.SearchRepository
import com.bowazon.bingewatcher.core.ui.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    val repository: DiscoverRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ScreenState<DiscoverUiModel>>(ScreenState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val showsBasedOnLikes = repository.showsBasedOnLikes()
            _state.value = ScreenState.Success(
                DiscoverUiModel(
                    showsBasedOnLikes = showsBasedOnLikes
                )
            )
        }
    }

}

data class DiscoverUiModel(
    val showsBasedOnLikes: List<ShowDetailed>
)
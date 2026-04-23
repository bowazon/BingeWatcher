package com.bowazon.bingewatcher.features.discover.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bowazon.bingewatcher.core.entities.EpisodeWithShow
import com.bowazon.bingewatcher.core.entities.ShowDetailed
import com.bowazon.bingewatcher.core.repository.DiscoverRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    val repository: DiscoverRepository
) : ViewModel() {

    private var currentDate = LocalDate.now()
    private val maxDate = currentDate.plusDays(6)

    //private val _state = MutableStateFlow<ScreenState<DiscoverUiModel>>(ScreenState.Loading)
    //val state = _state.asStateFlow()

    private val _shows = MutableStateFlow<List<ShowDetailed>>(emptyList())
    val shows = _shows.asStateFlow()

    private val _episodes = MutableStateFlow<List<EpisodeWithShow>>(emptyList())
    val episodes = _episodes.asStateFlow()

    private val _isLoadingMoreEpisodes = MutableStateFlow(false)
    val isLoadingMoreEpisodes = _isLoadingMoreEpisodes.asStateFlow()

    private val _isLoadedShows = MutableStateFlow(false)
    val isLoadedShows = _isLoadedShows.asStateFlow()

    init {
        viewModelScope.launch {
            repository.showsBasedOnLikes()
                .collect {
                    _shows.value = it
                    _isLoadedShows.value = true
                }
        }
    }

    fun loadNextDay() {
        if (_isLoadingMoreEpisodes.value) return
        if (currentDate >= maxDate) return

        viewModelScope.launch {

            _isLoadingMoreEpisodes.value = true

            currentDate = currentDate.plusDays(1)
            val newEpisodes = repository.getEpisodeSchedule(currentDate)

            _episodes.value = _episodes.value + newEpisodes

            _isLoadingMoreEpisodes.value = false
        }
    }
}

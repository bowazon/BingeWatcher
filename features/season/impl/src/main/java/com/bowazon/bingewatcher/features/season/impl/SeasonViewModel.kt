package com.bowazon.bingewatcher.features.season.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bowazon.bingewatcher.core.entities.Episode
import com.bowazon.bingewatcher.core.entities.Season
import com.bowazon.bingewatcher.core.repository.SeasonRepository
import com.bowazon.bingewatcher.core.ui.ScreenState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = SeasonViewModel.Factory::class)
class SeasonViewModel @AssistedInject constructor(
    @Assisted("showId") val showId: Int,
    @Assisted("seasonId") val seasonId: Int,
    val repository: SeasonRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ScreenState<SeasonUiModel>>(ScreenState.Loading)
    val state: StateFlow<ScreenState<SeasonUiModel>> = _state.asStateFlow() // TODO "= _state" or "= _state.asStateFlow()"???

    private val _effects = MutableSharedFlow<SeasonEffect>()
    val effects = _effects.asSharedFlow()

    init {
        viewModelScope.launch {
            try {
                coroutineScope {
                    val season = async { repository.getSeasonInfo(showId, seasonId) }
                    val episodes = async { repository.getEpisodes(seasonId) }

                    _state.value = ScreenState.Success(
                        SeasonUiModel(
                            season.await(),
                            episodes.await()
                        )
                    )
                }
            } catch (e: Exception) { // TODO generic error bad
                _state.value = ScreenState.Error
            }
        }
    }

    fun onEvent(event: SeasonEvent) {
        when(event) {
            is SeasonEvent.OnWatchedClicked -> {
                // TODO move to reduce
                viewModelScope.launch {
                    // 1. So first, we're getting current value
                    val currentState = _state.value
                    val data = (currentState as? ScreenState.Success<SeasonUiModel>)?.data ?: return@launch

                    val episode = data.episodes.find { it.id == event.episodeId } ?: return@launch
                    val newValue = !episode.watched // TOGGLE HERE

                    // 2. Then, we're marking loading (via isUpdatingWatched = true)
                    _state.update { oldState ->
                        val oldData = (oldState as? ScreenState.Success<SeasonUiModel>)?.data ?: return@update oldState
                        val updatedEpisodes = oldData.episodes.map {
                            if (it.id == event.episodeId) it.copy(isUpdatingWatched = true) else it
                        }
                        oldState.copy(data = oldData.copy(episodes = updatedEpisodes))
                    }

                    try {
                        // 3. Request to repo
                        delay(1000) // emulate IO
                        val result = newValue // emulate data from repo

                        // 4. Apply result
                        _state.update { oldState ->
                            val oldData = (oldState as? ScreenState.Success<SeasonUiModel>)?.data ?: return@update oldState
                            val updatedEpisodes = oldData.episodes.map {
                                if (it.id == event.episodeId) it.copy(
                                    watched = result,
                                    isUpdatingWatched = false
                                ) else it
                            }
                            oldState.copy(data = oldData.copy(episodes = updatedEpisodes))
                        }

                    } catch(e: Exception) {
                        // 5. If error just mark it not loading
                        _state.update { oldState ->
                            val oldData = (oldState as? ScreenState.Success<SeasonUiModel>)?.data ?: return@update oldState
                            val updatedEpisodes = oldData.episodes.map {
                                if (it.id == event.episodeId) it.copy(isUpdatingWatched = false) else it
                            }
                            oldState.copy(data = oldData.copy(episodes = updatedEpisodes))
                        }
                    }
                }
            }

            is SeasonEvent.OnLikeClicked -> {

            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("showId") showId: Int,
            @Assisted("seasonId") seasonId: Int
        ): SeasonViewModel
    }
}



/*
sealed class SeasonUiState {
    object Error: SeasonUiState()
    object Loading: SeasonUiState()
    data class Success(val data: SeasonUiModel): SeasonUiState()
}

 */

// TODO probably should be it's own dumb model
data class SeasonUiModel(
    val season: Season,
    val episodes: List<Episode>
)

sealed interface SeasonEvent {
    data class OnLikeClicked(val seasonId: Int) : SeasonEvent
    data class OnWatchedClicked(val episodeId: Int) : SeasonEvent
}

// TODO no need OnWatchedLoading OnWatchedLoaded - to delete
sealed interface SeasonEffect {
    data class OnWatchedLoading(val episodeId: Int) : SeasonEffect

    // result - result of operation, not the actual result of toggle state!
    data class OnWatchedLoaded(val episodeId: Int, val result: Boolean) : SeasonEffect
}

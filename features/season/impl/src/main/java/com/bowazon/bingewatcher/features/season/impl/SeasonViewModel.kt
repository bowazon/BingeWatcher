package com.bowazon.bingewatcher.features.season.impl

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bowazon.bingewatcher.core.entities.Season
import com.bowazon.bingewatcher.core.repository.SeasonRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = SeasonViewModel.Factory::class)
class SeasonViewModel @AssistedInject constructor(
    @Assisted("showId") val showId: Int,
    @Assisted("seasonId") val seasonId: Int,
    val repository: SeasonRepository
) : ViewModel() {

    private val _state = MutableStateFlow<SeasonUiState>(SeasonUiState.Loading)
    val state: StateFlow<SeasonUiState> = _state.asStateFlow() // TODO "= _state" or "= _state.asStateFlow()"???

    private val _effects = MutableSharedFlow<SeasonEffect>()
    val effects = _effects.asSharedFlow()

    init {
        viewModelScope.launch {
            try {
                coroutineScope {
                    Log.i("ART_DBG", "showId is " + showId + "; seasonId is " + seasonId)
                    val season = async { repository.getSeasonInfo(showId, seasonId) }

                    _state.value = SeasonUiState.Success(
                        SeasonUiModel(
                            season.await(),
                        )
                    )
                }
            } catch (e: Exception) { // TODO generic error bad
                _state.value = SeasonUiState.Error
                Log.i("ART_DBG", "error is " + e.toString())
            }
        }
    }

    fun onEvent(event: SeasonEvent) {

    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("showId") showId: Int,
            @Assisted("seasonId") seasonId: Int
        ): SeasonViewModel
    }
}


sealed class SeasonUiState {
    object Error: SeasonUiState()
    object Loading: SeasonUiState()
    data class Success(val data: SeasonUiModel): SeasonUiState()
}

// TODO probably should be it's own dumb model
data class SeasonUiModel(
    val season: Season
)

sealed interface SeasonEvent {
    //object OnImdbClick : SeasonEvent
    //object OnOfficialSiteClick : SeasonEvent
    object OnEpisodeClick : SeasonEvent
}

sealed interface SeasonEffect {
}

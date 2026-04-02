package com.example.features.show_details.impl

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.entities.Actor
import com.example.entities.Season
import com.example.entities.ShowDetailed
import com.example.repository.ShowRepository
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel(assistedFactory = ShowDetailsViewModel.Factory::class)
class ShowDetailsViewModel @AssistedInject constructor(
    @Assisted val showId: Int,
    val repository: ShowRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ShowDetailsUiState>(ShowDetailsUiState.Loading)
    val state: StateFlow<ShowDetailsUiState> = _state

    private val _effects = MutableSharedFlow<ShowDetailsEffect>()
    val effects = _effects.asSharedFlow()

    init {
        viewModelScope.launch {
            // TODO exceptions handler? Supervisor Job?
            try {
                coroutineScope {
                    val showDetails = async { repository.getShow(showId) }
                    val cast = async { repository.getActorsForShow(showId) }
                    val pictures = async { repository.getImagesForShow(showId) }
                    val seasons = async { repository.getSeasonsForShow(showId) }

                    _state.value = ShowDetailsUiState.Success(
                        ShowDetailsUiModel(
                            showDetails.await(),
                            cast.await(),
                            pictures.await(),
                            seasons.await()
                        )
                    )
                }
            } catch (e: Exception) { // TODO generic error bad
                _state.value = ShowDetailsUiState.Error
            }
        }
    }

    // TODO do we need to return ShowDetailsUiModel? We don't mutate state
    fun redux(event: ShowDetailsEvent) /* : ShowDetailsUiModel*/ {
        when (event) {
            is ShowDetailsEvent.OnImdbClick -> {
                // TODO should have analytics
                viewModelScope.launch {
                    if (_state.value is ShowDetailsUiState.Success) {
                        (_state.value as ShowDetailsUiState.Success).data.showDetails?.let {
                            _effects.emit(ShowDetailsEffect.OpenUrl(it.imdb))
                        }
                    }
                }
            }
            is ShowDetailsEvent.OnOfficialSiteClick -> {
                viewModelScope.launch {
                    if (_state.value is ShowDetailsUiState.Success) {
                        (_state.value as ShowDetailsUiState.Success).data.showDetails?.let {
                            _effects.emit(ShowDetailsEffect.OpenUrl(it.officialSite))
                        }
                    }
                }
            }
            is ShowDetailsEvent.OnFollowClick -> {
                // TODO
                // to follow repository
            }
            is ShowDetailsEvent.OnSeasonClick -> {

            }
            is ShowDetailsEvent.OnActorClick -> {

            }
            is ShowDetailsEvent.OnGalleryClick -> {

            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            showId: Int,
        ): ShowDetailsViewModel
    }
}

sealed class ShowDetailsUiState {
    object Error: ShowDetailsUiState()
    object Loading: ShowDetailsUiState()
    // TODO data class contains data class
    data class Success(val data: ShowDetailsUiModel): ShowDetailsUiState()
}

data class ShowDetailsUiModel(
    val showDetails: ShowDetailed,
    val cast: List<Actor>,
    val pictures: List<String>,
    val seasons: List<Season>
)

sealed interface ShowDetailsEvent {
    object OnImdbClick : ShowDetailsEvent
    object OnOfficialSiteClick : ShowDetailsEvent
    object OnFollowClick : ShowDetailsEvent
    data class OnSeasonClick(val s: Int) : ShowDetailsEvent
    data class OnActorClick(val id: Int) : ShowDetailsEvent
    data class OnGalleryClick(val i: Int) : ShowDetailsEvent
}

sealed interface ShowDetailsEffect {
    data class OpenUrl(val url: String) : ShowDetailsEffect
}

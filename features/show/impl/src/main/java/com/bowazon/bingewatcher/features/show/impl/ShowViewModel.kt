package com.bowazon.bingewatcher.features.show.impl

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bowazon.bingewatcher.core.entities.ActorSummary
import com.bowazon.bingewatcher.core.entities.CrewSummary
import com.bowazon.bingewatcher.core.entities.Season
import com.bowazon.bingewatcher.core.entities.ShowDetailed
import com.bowazon.bingewatcher.core.repository.ShowRepository
import com.bowazon.bingewatcher.core.ui.ScreenState
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

@HiltViewModel(assistedFactory = ShowViewModel.Factory::class)
class ShowViewModel @AssistedInject constructor(
    @Assisted val showId: Int,
    val repository: ShowRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ScreenState<ShowUiModel>>(ScreenState.Loading)
    val state: StateFlow<ScreenState<ShowUiModel>> = _state

    private val _effects = MutableSharedFlow<ShowScreenEffect>()
    val effects = _effects.asSharedFlow()

    init {
        viewModelScope.launch {
            // TODO exceptions handler? Supervisor Job?
            try {
                coroutineScope {
                    val show = async { repository.getShow(showId) }
                    val cast = async { repository.getActorsForShow(showId) }
                    //val crew = async { repository.getCrewForShow(showId) }
                    val pictures = async { repository.getImagesForShow(showId) }
                    val seasons = async { repository.getSeasonsForShow(showId) }
                    /*
                    val crewMerged = crew
                        .await()
                        .groupBy { crewSummary ->
                            crewSummary.name
                        }.map { (_, personsCrewRoles) ->
                            val first = personsCrewRoles.first()

                            val mergedRoles = personsCrewRoles
                                .map { it.role }
                                .distinct()
                                .joinToString("\n")

                            first.copy(role = mergedRoles)
                        }
                        */

                    _state.value = ScreenState.Success(
                        ShowUiModel(
                            show.await(),
                            cast.await(),
                            //crewMerged,
                            pictures.await(),
                            seasons.await()
                        )
                    )
                }
            } catch (e: Exception) { // TODO generic error bad
                _state.value = ScreenState.Error
                Log.i("ART_DBG_5", "e is " + e.toString())
            }
        }
    }

    fun onEvent(event: ShowScreenEvent) {
        when (event) {
            is ShowScreenEvent.OnImdbClick -> {
                // TODO should have analytics
                val currentState = _state.value
                if (currentState is ScreenState.Success) {
                    openUrl(currentState.data.show.imdb)
                }
            }
            is ShowScreenEvent.OnOfficialSiteClick -> {
                val currentState = _state.value
                if (currentState is ScreenState.Success) {
                    openUrl(currentState.data.show.officialSite)
                }
            }
            is ShowScreenEvent.OnFollowClick -> {
                // TODO
                // to follow repository
            }
            is ShowScreenEvent.OnSeasonClick -> {
                // TODO showId and seasonId is good place to use inline value classes
                val showId = event.showId
                val seasonId = event.seasonId
                viewModelScope.launch {
                    _effects.emit(ShowScreenEffect.OpenSeasonScreen(
                        showId,
                        seasonId
                    ))
                }
            }
            is ShowScreenEvent.OnPersonClick -> {
                val personId = event.id
                viewModelScope.launch {
                    _effects.emit(ShowScreenEffect.OpenPersonScreen(personId))
                }
            }
            is ShowScreenEvent.OnFullCrewClick -> {
                val showId = event.showId
                viewModelScope.launch {
                    _effects.emit(ShowScreenEffect.OpenFullCrewScreen(showId))
                }
            }
            is ShowScreenEvent.OnGalleryClick -> {
                val imageIndex = event.imageIndex
                val urlList = event.urlList
                viewModelScope.launch {
                    _effects.emit(ShowScreenEffect.OpenImageView(imageIndex, urlList))
                }
            }
        }
    }

    private fun openUrl(url: String) {
        viewModelScope.launch {
            _effects.emit(ShowScreenEffect.OpenUrl(url))
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            showId: Int,
        ): ShowViewModel
    }
}

// TODO dumb model
data class ShowUiModel(
    val show: ShowDetailed,
    val cast: List<ActorSummary>,
    //val crew: List<CrewSummary>,
    val pictures: List<String>,
    val seasons: List<Season>
)

sealed interface ShowScreenEvent {
    object OnImdbClick : ShowScreenEvent
    object OnOfficialSiteClick : ShowScreenEvent
    object OnFollowClick : ShowScreenEvent
    data class OnSeasonClick(val showId: Int, val seasonId: Int) : ShowScreenEvent
    data class OnPersonClick(val id: Int) : ShowScreenEvent
    data class OnFullCrewClick(val showId: Int) : ShowScreenEvent
    data class OnGalleryClick(val imageIndex: Int, val urlList: List<String>) : ShowScreenEvent
}

sealed interface ShowScreenEffect {
    data class OpenUrl(val url: String) : ShowScreenEffect
    data class OpenPersonScreen(val id: Int) : ShowScreenEffect
    data class OpenSeasonScreen(val showId: Int, val seasonId: Int) : ShowScreenEffect
    data class OpenFullCrewScreen(val showId: Int) : ShowScreenEffect
    data class OpenImageView(val imageIndex: Int, val urlList: List<String>) : ShowScreenEffect
}

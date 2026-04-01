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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel(assistedFactory = ShowDetailsViewModel.Factory::class)
class ShowDetailsViewModel @AssistedInject constructor(
    @Assisted val showDetailed: ShowDetailed,
    val repository: ShowRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ShowDetailsUiModel?>(null)
    val state: StateFlow<ShowDetailsUiModel?> = _state

    init {
        viewModelScope.launch {
            // TODO exceptions handler? Supervisor Job?
            val cast = async { repository.getActorsForShow(showDetailed.id) }
            val pictures = async { repository.getImagesForShow(showDetailed.id) }
            val seasons = async { repository.getSeasonsForShow(showDetailed.id) }

            _state.value = ShowDetailsUiModel(
                name = showDetailed.summary.name,
                rating = showDetailed.summary.rating,
                genres = showDetailed.summary.genres,
                premiered = showDetailed.summary.premiered,
                imdb = showDetailed.imdb,
                ended = showDetailed.ended,
                officialSite = showDetailed.officialSite,
                description = showDetailed.description,
                cast = cast.await(),
                pictures = pictures.await(),
                seasons = seasons.await()
            )
        }
    }

    // TODO do we need to return ShowDetailsUiModel? We don't mutate state
    fun redux(event: ShowDetailsEvent) /* : ShowDetailsUiModel*/ {
        when (event) {
            is ShowDetailsEvent.OnImdbClick -> {
                // TODO should have analytics
                Log.i("ART_DBG", "ShowDetailsEvent.OnImdbClick")
                Log.i("ART_DBG", "showDetailed is " + showDetailed.toString())
            }
            is ShowDetailsEvent.OnOfficialSiteClick -> {

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
            show: ShowDetailed,
        ): ShowDetailsViewModel
    }
}


data class ShowDetailsUiModel(
    val name: String,
    val rating: String,
    val genres: String,
    val premiered: String,
    val imdb: String,
    val ended: String,
    val officialSite: String,
    val description: String,
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
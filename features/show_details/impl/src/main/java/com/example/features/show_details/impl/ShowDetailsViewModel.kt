package com.example.features.show_details.impl

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.entities.ShowDetailed
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel(assistedFactory = ShowDetailsViewModel.Factory::class)
class ShowDetailsViewModel @AssistedInject constructor(
    @Assisted val showDetailed: ShowDetailed
) : ViewModel() {


    fun redux(event: ShowDetailsEvent): ShowDetailsUiModel {
        when (event) {
            is ShowDetailsEvent.OnImdbClick -> {
                // TODO should have analytics
                Log.i("ART_DBG", "ShowDetailsEvent.OnImdbClick")
                Log.i("ART_DBG", "showDetailed is " + showDetailed.toString())
            }
            is ShowDetailsEvent.OnOfficialSiteClick -> {

            }
            is ShowDetailsEvent.OnFollowClick -> {

            }
            is ShowDetailsEvent.OnSeasonClick -> {

            }
            is ShowDetailsEvent.OnActorClick -> {

            }
            is ShowDetailsEvent.OnGalleryClick -> {

            }
        }
        return ShowDetailsUiModel(
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
        )
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
)

sealed interface ShowDetailsEvent {
    object OnImdbClick : ShowDetailsEvent
    object OnOfficialSiteClick : ShowDetailsEvent
    object OnFollowClick : ShowDetailsEvent
    data class OnSeasonClick(val s: Int) : ShowDetailsEvent
    data class OnActorClick(val id: Int) : ShowDetailsEvent
    data class OnGalleryClick(val i: Int) : ShowDetailsEvent
}
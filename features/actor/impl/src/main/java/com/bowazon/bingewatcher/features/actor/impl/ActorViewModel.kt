package com.bowazon.bingewatcher.features.actor.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bowazon.bingewatcher.core.entities.ActorDetailed
import com.bowazon.bingewatcher.core.repository.ActorRepository
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

@HiltViewModel(assistedFactory = ActorViewModel.Factory::class)
class ActorViewModel @AssistedInject constructor(
    @Assisted val actorId: Int,
    val repository: ActorRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ActorUiState>(ActorUiState.Loading)
    val state: StateFlow<ActorUiState> = _state.asStateFlow() // TODO "= _state" or "= _state.asStateFlow()"???

    private val _effects = MutableSharedFlow<ActorEffect>()
    val effects = _effects.asSharedFlow()

    init {
        viewModelScope.launch {
            try {
                coroutineScope {
                    val actorDetails = async { repository.getActorInfo(actorId) }

                    _state.value = ActorUiState.Success(
                        ActorUiModel(
                            actorDetails.await(),
                        )
                    )
                }
            } catch (e: Exception) { // TODO generic error bad
                _state.value = ActorUiState.Error
            }
        }
    }

    fun onEvent(event: ActorEvent) {

    }

    @AssistedFactory
    interface Factory {
        fun create(
            showId: Int,
        ): ActorViewModel
    }
}


sealed class ActorUiState {
    object Error: ActorUiState()
    object Loading: ActorUiState()
    data class Success(val data: ActorUiModel): ActorUiState()
}

// TODO probably should be it's own dumb model
data class ActorUiModel(
    val actorDetailed: ActorDetailed
)

sealed interface ActorEvent {
    //object OnImdbClick : ActorEvent
    //object OnOfficialSiteClick : ActorEvent
    object OnFollowClick : ActorEvent
    data class OnCharacterClick(val s: Int) : ActorEvent
    data class OnShowClick(val id: Int) : ActorEvent
}

sealed interface ActorEffect {
    //data class OpenUrl(val url: String) : ActorEffect
    //data class OpenActorCard(val id: Int) : ActorEffect
}

package com.bowazon.bingewatcher.features.person.impl

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bowazon.bingewatcher.core.entities.ActorCredits
import com.bowazon.bingewatcher.core.entities.ActorDetailed
import com.bowazon.bingewatcher.core.repository.PersonRepository
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

@HiltViewModel(assistedFactory = PersonViewModel.Factory::class)
class PersonViewModel @AssistedInject constructor(
    @Assisted val personId: Int,
    val repository: PersonRepository
) : ViewModel() {

    private val _state = MutableStateFlow<PersonUiState>(PersonUiState.Loading)
    val state: StateFlow<PersonUiState> = _state.asStateFlow() // TODO "= _state" or "= _state.asStateFlow()"???

    private val _effects = MutableSharedFlow<PersonEffect>()
    val effects = _effects.asSharedFlow()

    init {
        viewModelScope.launch {
            try {
                coroutineScope {
                    val actorDetails = async { repository.getActorInfo(personId) }
                    val actorCredits = async { repository.getActorCredits(personId) }

                    _state.value = PersonUiState.Success(
                        PersonUiModel(
                            actorDetails.await(),
                            actorCredits.await()
                        )
                    )
                }
            } catch (e: Exception) { // TODO generic error bad
                _state.value = PersonUiState.Error
                Log.i("ART_DBG", "e is " + e.toString())
            }
        }
    }

    fun onEvent(event: PersonEvent) {

    }

    @AssistedFactory
    interface Factory {
        fun create(
            personId: Int,
        ): PersonViewModel
    }
}


sealed class PersonUiState {
    object Error: PersonUiState()
    object Loading: PersonUiState()
    data class Success(val data: PersonUiModel): PersonUiState()
}

// TODO probably should be it's own dumb model
data class PersonUiModel(
    val personDetailed: ActorDetailed,
    val personCredits: List<ActorCredits>
)

sealed interface PersonEvent {
    //object OnImdbClick : ActorEvent
    //object OnOfficialSiteClick : ActorEvent
    object OnFollowClick : PersonEvent
    data class OnCharacterClick(val s: Int) : PersonEvent
    data class OnShowClick(val id: Int) : PersonEvent
}

sealed interface PersonEffect {
    //data class OpenUrl(val url: String) : ActorEffect
    //data class OpenActorCard(val id: Int) : ActorEffect
}

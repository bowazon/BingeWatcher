package com.bowazon.bingewatcher.features.person.impl

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bowazon.bingewatcher.core.entities.ActorCredits
import com.bowazon.bingewatcher.core.entities.CrewCredits
import com.bowazon.bingewatcher.core.entities.Person
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
import kotlin.collections.component1
import kotlin.collections.component2

// TODO here and in every view model - inject scope or dispatcher
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
                    val personDetails = async { repository.getPersonInfo(personId) }
                    val actorCredits = async { repository.getActorCredits(personId) }
                    val crewCredits = async { repository.getCrewCredits(personId) }

                    val p = personDetails.await()
                    val a = actorCredits.await()
                    val c = crewCredits.await()

                    val mergedCrew = c
                        .groupBy { it.showId }
                        .map { (_, shows) ->
                            val first = shows.first()

                            val mergedRoles = shows
                                .map { it.type }
                                .distinct()
                                .joinToString("/")

                            first.copy(type = mergedRoles)
                        }

                    _state.value = PersonUiState.Success(
                        PersonUiModel(
                            getCreditsSummary(p, a, c),
                            p, a, mergedCrew
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
        when(event) {
            is PersonEvent.OnShowClick -> {
                viewModelScope.launch {
                    _effects.emit(PersonEffect.OpenShowCard(event.id))
                }
            }
            is PersonEvent.OnFollowClick -> {

            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            personId: Int,
        ): PersonViewModel
    }

    private fun hasRole(crewCredits: List<CrewCredits>, role: String): Boolean {
        return crewCredits.any{ it.type.contains(role, ignoreCase = true) }
    }

    private fun getCreditsSummary(
        personDetailed: Person,
        actorCredits: List<ActorCredits>,
        crewCredits: List<CrewCredits>
    ): String {
        val gender = personDetailed.gender

        val roles = listOfNotNull(
                if (actorCredits.isNotEmpty()) {
                    if (gender == "Male") "Actor"
                    else "Actress"
                } else null,
                if (hasRole(crewCredits, "Director")) "Director" else null,
                if (hasRole(crewCredits, "Writer")) "Writer" else null,
                if (hasRole(crewCredits, "Producer")) "Producer" else null)

        return roles.joinToString(separator = " · ")
    }
}

// TODO here and in every viewmodel - it probably should be common
sealed class PersonUiState {
    object Error: PersonUiState()
    object Loading: PersonUiState()
    data class Success(val data: PersonUiModel): PersonUiState()
}

// TODO probably should be it's own dumb model
data class PersonUiModel(
    val creditsSummary: String,
    val personDetailed: Person,
    val actorCredits: List<ActorCredits>,
    val crewCredits: List<CrewCredits>
)

sealed interface PersonEvent {
    object OnFollowClick : PersonEvent
    data class OnShowClick(val id: Int) : PersonEvent
}

sealed interface PersonEffect {
    data class OpenShowCard(val id: Int) : PersonEffect
}

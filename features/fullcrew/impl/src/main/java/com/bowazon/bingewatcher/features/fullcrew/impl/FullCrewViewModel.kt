package com.bowazon.bingewatcher.features.fullcrew.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bowazon.bingewatcher.core.entities.ActorCredits
import com.bowazon.bingewatcher.core.entities.CrewCredits
import com.bowazon.bingewatcher.core.entities.CrewSummary
import com.bowazon.bingewatcher.core.entities.Person
import com.bowazon.bingewatcher.core.repository.FullCrewRepository
import com.bowazon.bingewatcher.core.repository.FullCrewRequest
import com.bowazon.bingewatcher.core.ui.ScreenState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


@HiltViewModel(assistedFactory = FullCrewViewModel.Factory::class)
class FullCrewViewModel @AssistedInject constructor(
    @Assisted val request: FullCrewRequest,
    val repository: FullCrewRepository // TODO make separate repositories for show and for episode and inject according to type
) : ViewModel() {

    private val _state = MutableStateFlow<ScreenState<FullCrewUiModel>>(ScreenState.Loading)
    val state: StateFlow<ScreenState<FullCrewUiModel>> = _state.asStateFlow() // TODO "= _state" or "= _state.asStateFlow()"???

    private val _effects = MutableSharedFlow<FullCrewEffect>()
    val effects = _effects.asSharedFlow()

    init {
        viewModelScope.launch {
            try {
                val crew = repository.getFullCrew(request)
                _state.value = ScreenState.Success(
                    FullCrewUiModel(crew)
                )
            } catch (e: Exception) {
                _state.value = ScreenState.Error
            }
        }
    }

    fun onEvent(event: FullCrewEvent) {

    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted request: FullCrewRequest,
        ): FullCrewViewModel
    }
}

data class FullCrewUiModel(
    val crew: List<CrewSummary>
)

sealed interface FullCrewEvent {
    object OnFollowClick : FullCrewEvent
    data class OnShowClick(val id: Int) : FullCrewEvent
}

sealed interface FullCrewEffect {
    data class OpenShowCard(val id: Int) : FullCrewEffect
}

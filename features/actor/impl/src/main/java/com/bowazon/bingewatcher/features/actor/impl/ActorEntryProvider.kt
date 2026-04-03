package com.bowazon.bingewatcher.features.actor.impl

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.bowazon.bingewatcher.core.navigation.Navigator
import com.bowazon.bingewatcher.features.actor.api.ActorNavKey

fun EntryProviderScope<NavKey>.actorEntry(navigator: Navigator) {
    entry<ActorNavKey> { navKey ->
        ActorScreen(
            onBackClicked = navigator::goBack,
            viewModel = hiltViewModel<ActorViewModel, ActorViewModel.Factory>(
                // TODO all these "" things should be part of companion objects of view models or smth
                key = "actor_${navKey.actorId}",
            ) { factory ->
                factory.create(navKey.actorId)
            }
        )
    }
}
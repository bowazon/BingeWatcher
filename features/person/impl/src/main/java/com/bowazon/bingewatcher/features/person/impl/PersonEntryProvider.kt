package com.bowazon.bingewatcher.features.person.impl

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.bowazon.bingewatcher.core.navigation.Navigator
import com.bowazon.bingewatcher.features.person.api.PersonNavKey
import com.bowazon.bingewatcher.features.show.api.navigateToShowDetails

fun EntryProviderScope<NavKey>.personEntry(navigator: Navigator) {
    entry<PersonNavKey> { navKey ->
        PersonScreen(
            onShowClicked = navigator::navigateToShowDetails,
            onBackClicked = navigator::goBack,
            viewModel = hiltViewModel<PersonViewModel, PersonViewModel.Factory>(
                // TODO all these "" things should be part of companion objects of view models or smth
                key = "person_${navKey.personId}",
            ) { factory ->
                factory.create(navKey.personId)
            }
        )
    }
}
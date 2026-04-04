package com.bowazon.bingewatcher.features.imageview.impl

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.bowazon.bingewatcher.core.navigation.Navigator
import com.bowazon.bingewatcher.features.imageview.api.ImageViewNavKey
import kotlin.String

fun EntryProviderScope<NavKey>.imageViewEntry(navigator: Navigator) {
    entry<ImageViewNavKey> { navKey ->
        ImageViewScreen(
            imageIndex = navKey.imageIndex,
            urlList = navKey.urlList
        )
    }
}
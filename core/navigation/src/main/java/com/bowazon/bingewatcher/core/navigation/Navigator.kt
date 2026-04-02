package com.bowazon.bingewatcher.core.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey

class Navigator(
    val navBackStack: NavBackStack<NavKey>
) {

    fun navigate(key: NavKey) {
        navBackStack.add(key)
    }

    fun goBack() {
        navBackStack.removeLastOrNull()
    }
}

fun Navigator.toEntries(
    entryProvider: (NavKey) -> NavEntry<NavKey>
): List<NavEntry<NavKey>> {
    return navBackStack.map { entryProvider(it) }
}
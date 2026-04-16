package com.bowazon.bingewatcher.core.entities

interface CrewSource

data class FullCrew<out T : CrewSource>(
    val ofWhat: T,
    val crew: List<CrewSummary>
)
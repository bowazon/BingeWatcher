package com.bowazon.bingewatcher.core.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeLinks(
    val self: TvMazeSelf ?= null,
    val show: TvMazeShowLink ?= null,
)
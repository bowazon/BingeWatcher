package com.example.network.tvmaze.models

data class TvMazeActor(
    val person: TvMazePerson,
    val character: TvMazeCharacter,
    val self: Boolean,
    val voice: Boolean,
)
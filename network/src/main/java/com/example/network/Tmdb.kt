package com.example.network

import info.movito.themoviedbapi.TmdbApi
import info.movito.themoviedbapi.TmdbMovies
import info.movito.themoviedbapi.model.core.responses.TmdbResponseException
import info.movito.themoviedbapi.tools.TmdbException
import info.movito.themoviedbapi.tools.TmdbResponseCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// https://github.com/c-eg/themoviedbapi

class Tmdb {
    val api = TmdbApi(API_KEY)

    // TODO put somewhere else
    companion object {
        const val API_KEY = "1176667306b4ed369193d05d0de41407"
    }
}

// errors
// https://developer.themoviedb.org/docs/errors

suspend fun Tmdb.getMoviesSuspend(): TmdbMovies = withContext(Dispatchers.IO) {
    try {
        this@getMoviesSuspend.api.movies
    } catch (exception: TmdbResponseException) {
        val responseCode = exception.responseCode
        // handle unsuccessful TMdB response code
        throw exception
    } catch (exception: TmdbException) {
        // handle unknown-cause exception
        throw exception
    }
}
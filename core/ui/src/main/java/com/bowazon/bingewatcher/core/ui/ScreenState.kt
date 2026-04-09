package com.bowazon.bingewatcher.core.ui

sealed class ScreenState<out T> {
    object Error : ScreenState<Nothing>()
    object Loading : ScreenState<Nothing>()
    data class Success<T>(val data: T) : ScreenState<T>()
}
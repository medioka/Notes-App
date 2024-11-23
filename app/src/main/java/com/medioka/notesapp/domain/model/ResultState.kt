package com.medioka.notesapp.domain.model

sealed class ResultState<out T> {
    data object Default : ResultState<Nothing>()
    data object Loading : ResultState<Nothing>()
    data object Empty : ResultState<Nothing>()
    data class Error(val throwable: Throwable) : ResultState<Nothing>()
    data class Success<T>(val data: T) : ResultState<T>()
}

package com.aikonia.app.domain.repository

interface FirebaseRepository {
    suspend fun isThereUpdate(): Boolean
}
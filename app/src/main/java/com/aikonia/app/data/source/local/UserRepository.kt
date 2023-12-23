package com.aikonia.app.data.source.local

import com.aikonia.app.data.source.local.User

interface UserRepository {
    suspend fun saveUser(user: User)
    suspend fun getUserById(userId: Int): User?
    suspend fun getCurrentUserName(): String
    suspend fun getUserBirthYear(): Int // Hinzufügen dieser Methode
    suspend fun getUserGender(): String // Hinzufügen dieser Methode
}
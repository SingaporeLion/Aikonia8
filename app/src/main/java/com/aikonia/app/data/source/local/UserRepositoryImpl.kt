package com.aikonia.app.data.source.local

import com.aikonia.app.data.source.local.User
import com.aikonia.app.data.source.local.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.content.SharedPreferences

class UserRepositoryImpl(private val userDao: UserDao, private val sharedPreferences: SharedPreferences) : UserRepository {



    override suspend fun saveUser(user: User) {
        withContext(Dispatchers.IO) {
            val userId = userDao.insertUser(user)  // Nimmt an, dass insertUser die ID des eingefügten Benutzers zurückgibt
            sharedPreferences.edit().putInt("userIdKey", userId.toInt()).apply()
        }
    }

    override suspend fun getUserById(userId: Int): User? {
        return withContext(Dispatchers.IO) {
            userDao.getUserById(userId)
        }
    }

    override suspend fun getCurrentUserName(): String {
        // Hier die Benutzer-ID als Long abrufen
        val userId = sharedPreferences.getInt("userIdKey", -1).toLong()
        // Prüfen, ob eine gültige ID vorhanden ist
        if (userId != -1L) {
            val currentUser = userDao.getCurrentUser(userId.toInt())
            return currentUser.name
        } else {
            // Fallback, falls keine Benutzer-ID gespeichert ist
            return "Unbekannter Benutzer"
        }
    }

    override suspend fun getUserBirthYear(): Int {
        val userId = sharedPreferences.getInt("userIdKey", -1).toLong()
        if (userId != -1L) {
            val currentUser = userDao.getCurrentUser(userId.toInt())
            return currentUser.birthYear.toIntOrNull() ?: -1 // Konvertiert den String in Int, -1 als Fallback
        } else {
            return -1 // oder ein angemessener Fallback-Wert
        }
    }

    override suspend fun getUserGender(): String {
        val userId = sharedPreferences.getInt("userIdKey", -1).toLong()
        if (userId != -1L) {
            val currentUser = userDao.getCurrentUser(userId.toInt())
            return currentUser.gender // Annahme, dass das Geschlecht als gender im User-Objekt gespeichert ist
        } else {
            return "Unbekannt" // oder ein angemessener Fallback-Wert
        }
    }
}
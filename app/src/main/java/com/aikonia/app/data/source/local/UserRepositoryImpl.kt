package com.aikonia.app.data.source.local

import com.aikonia.app.data.source.local.User
import com.aikonia.app.data.source.local.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.content.SharedPreferences

class UserRepositoryImpl(private val userDao: UserDao, private val sharedPreferences: SharedPreferences) : UserRepository {


    override suspend fun saveUser(user: User) {
        withContext(Dispatchers.IO) {
            userDao.insertUser(user)
        }
    }

    override suspend fun getUserById(userId: Int): User? {
        return withContext(Dispatchers.IO) {
            userDao.getUserById(userId)
        }
    }

    override suspend fun getCurrentUserName(): String {
        // Hier die Benutzer-ID abrufen
        val userId = sharedPreferences.getInt("userIdKey", -1)
        // Prüfen, ob eine gültige ID vorhanden ist
        if (userId != -1) {
            val currentUser = userDao.getCurrentUser(userId)
            return currentUser.name
        } else {
            // Fallback, falls keine Benutzer-ID gespeichert ist
            return "Unbekannter Benutzer"
        }
    }
}
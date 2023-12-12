package com.aikonia.app.data.source.local


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aikonia.app.data.source.local.User  // Stellen Sie sicher, dass der richtige Importpfad für die User-Klasse verwendet wird

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM user_table WHERE id = :userId")
    suspend fun getUserById(userId: Int): User?

    @Query("SELECT * FROM user_table WHERE id = :userId")
    suspend fun getCurrentUser(userId: Int): User
}


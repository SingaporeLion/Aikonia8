package com.aikonia.app.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aikonia.app.data.model.ConversationModel
import com.aikonia.app.data.model.MessageModel
import com.aikonia.app.data.source.local.User // Import für die User-Entity

@Database(
    entities = [ConversationModel::class, MessageModel::class, User::class], // User zur Liste hinzufügen
    version = 2,
    exportSchema = false
)
abstract class ConversAIDatabase : RoomDatabase() {

    abstract fun conversAIDao(): ConversAIDao
    abstract fun userDao(): UserDao  // DAO für User

}
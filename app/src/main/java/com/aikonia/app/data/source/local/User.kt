package com.aikonia.app.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val birthYear: String,
    val gender: String
)
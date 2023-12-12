package com.aikonia.app.di

import android.content.Context
import android.content.SharedPreferences // Import für SharedPreferences
import androidx.room.Room
import com.aikonia.app.data.source.local.ConversAIDatabase
import com.aikonia.app.data.source.local.UserDao
import com.aikonia.app.data.source.local.UserRepository
import com.aikonia.app.data.source.local.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RoomDbModule {

    @Provides
    @Singleton
    fun provideRoomDb(@ApplicationContext appContext: Context): ConversAIDatabase =
        Room.databaseBuilder(
            appContext,
            ConversAIDatabase::class.java,
            "conversAIdb.db"
        )
            .fallbackToDestructiveMigration() // Diese Zeile hinzufügen
            .build()

    @Provides
    @Singleton
    fun provideConversAIDao(conversAIDatabase: ConversAIDatabase) = conversAIDatabase.conversAIDao()

    @Provides
    @Singleton
    fun provideUserDao(db: ConversAIDatabase): UserDao = db.userDao()

    //@Provides
    //@Singleton
    //fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences {
    //    return appContext.getSharedPreferences("name_of_your_preference_file", Context.MODE_PRIVATE)
    //}


    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao, sharedPreferences: SharedPreferences): UserRepository {
        return UserRepositoryImpl(userDao, sharedPreferences)
    }
}

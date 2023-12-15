package com.aikonia.app.ui.startchat

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aikonia.app.data.source.local.User
import com.aikonia.app.data.source.local.UserRepository
import com.aikonia.app.domain.use_case.app.IsThereUpdateUseCase
import com.aikonia.app.domain.use_case.language.GetCurrentLanguageCodeUseCase
import com.aikonia.app.domain.use_case.upgrade.IsFirstTimeUseCase
import com.aikonia.app.domain.use_case.upgrade.IsProVersionUseCase
import com.aikonia.app.domain.use_case.upgrade.SetFirstTimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.content.SharedPreferences
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class StartChatViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val isProVersionUseCase: IsProVersionUseCase,
    //private val isFirstTimeUseCase: IsFirstTimeUseCase,
    //private val setFirstTimeUseCase: SetFirstTimeUseCase,
    private val isThereUpdateUseCase: IsThereUpdateUseCase,
    private val getCurrentLanguageCodeUseCase: GetCurrentLanguageCodeUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    val isProVersion = mutableStateOf(false)
    //val isFirstTime = mutableStateOf(true)
    val isThereUpdate = mutableStateOf(false)
    val currentLanguageCode = mutableStateOf("de")

    private val _isUserDataSaved = MutableStateFlow(false)
    val isUserDataSaved: StateFlow<Boolean> = _isUserDataSaved.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    init {
        loadCurrentUser()
    }

    fun isThereUpdate() = viewModelScope.launch {
        isThereUpdate.value = isThereUpdateUseCase()
    }

    //fun getFirstTime() = viewModelScope.launch {
    //    isFirstTime.value = isFirstTimeUseCase()
   // }

   // fun setFirstTime(isFirstTime: Boolean) = setFirstTimeUseCase(isFirstTime)

    fun getCurrentLanguageCode() = viewModelScope.launch {
        currentLanguageCode.value = getCurrentLanguageCodeUseCase()
    }

    fun saveUser(name: String, birthYear: String, gender: String) = viewModelScope.launch {
        userRepository.saveUser(User(name = name, birthYear = birthYear, gender = gender))
        _isUserDataSaved.value = true
        loadCurrentUser() // Update current user after user is saved
    }

    fun resetUserDataSavedStatus() {
        _isUserDataSaved.value = false
    }

    private fun loadCurrentUser() = viewModelScope.launch {
        val userId = sharedPreferences.getInt("userIdKey", -1)
        _currentUser.value = if (userId != -1) {
            userRepository.getUserById(userId)
        } else {
            null
        }
        _isUserDataSaved.value = _currentUser.value != null
    }

    fun checkUserDataExists(userId: Int) = viewModelScope.launch {
        val userExists = userRepository.getUserById(userId) != null
        _isUserDataSaved.value = userExists
    }
}
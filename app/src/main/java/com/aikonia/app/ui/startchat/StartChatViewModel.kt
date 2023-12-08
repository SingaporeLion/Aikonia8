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

@HiltViewModel
class StartChatViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val isProVersionUseCase: IsProVersionUseCase,
    private val isFirstTimeUseCase: IsFirstTimeUseCase,
    private val setFirstTimeUseCase: SetFirstTimeUseCase,
    private val isThereUpdateUseCase: IsThereUpdateUseCase,
    private val getCurrentLanguageCodeUseCase: GetCurrentLanguageCodeUseCase
) : ViewModel() {

    val isProVersion = mutableStateOf(false)
    val isFirstTime = mutableStateOf(true)
    val isThereUpdate = mutableStateOf(false)
    val currentLanguageCode = mutableStateOf("de")

    private val _isUserDataSaved = MutableStateFlow(false)
    val isUserDataSaved = _isUserDataSaved.asStateFlow()

    fun isThereUpdate() = viewModelScope.launch {
        isThereUpdate.value = isThereUpdateUseCase()
    }

    fun getProVersion() = viewModelScope.launch {
        isProVersion.value = isProVersionUseCase()
    }

    fun getFirstTime() = viewModelScope.launch {
        isFirstTime.value = isFirstTimeUseCase()
    }

    fun setFirstTime(isFirstTime: Boolean) = setFirstTimeUseCase(isFirstTime)

    fun getCurrentLanguageCode() = viewModelScope.launch {
        currentLanguageCode.value = getCurrentLanguageCodeUseCase()
    }

    fun saveUser(name: String, birthYear: String, gender: String) = viewModelScope.launch {
        userRepository.saveUser(User(name = name, birthYear = birthYear, gender = gender))
        _isUserDataSaved.value = true
    }

    fun resetUserDataSavedStatus() {
        _isUserDataSaved.value = false
    }

    fun getCurrentUserName(): String {
        var userName = ""
        viewModelScope.launch {
            userName = userRepository.getCurrentUserName()
        }
        return userName
    }

    fun checkUserDataExists(userId: Int) = viewModelScope.launch {
        val userExists = userRepository.getUserById(userId) != null
        _isUserDataSaved.value = userExists
    }
}
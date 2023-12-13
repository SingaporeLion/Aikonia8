package com.aikonia.app.ui.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aikonia.app.data.source.local.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeScreenViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun getCurrentUserName(onResult: (String) -> Unit) {
        viewModelScope.launch {
            val userName = userRepository.getCurrentUserName()
            onResult(userName)
        }
    }
}
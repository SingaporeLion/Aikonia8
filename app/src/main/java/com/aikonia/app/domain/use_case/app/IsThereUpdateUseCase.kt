package com.aikonia.app.domain.use_case.app

import com.aikonia.app.domain.repository.FirebaseRepository
import javax.inject.Inject

class IsThereUpdateUseCase @Inject constructor(private val firebaseRepository: FirebaseRepository) {
    operator suspend fun invoke(): Boolean {
        return firebaseRepository.isThereUpdate()
    }
}
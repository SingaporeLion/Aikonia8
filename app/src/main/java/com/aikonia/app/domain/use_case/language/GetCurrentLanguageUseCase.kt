package com.aikonia.app.domain.use_case.language

import com.aikonia.app.domain.repository.PreferenceRepository
import javax.inject.Inject

class GetCurrentLanguageUseCase @Inject constructor(private val preferenceRepository: PreferenceRepository) {
    operator fun invoke() = preferenceRepository.getCurrentLanguage()
}
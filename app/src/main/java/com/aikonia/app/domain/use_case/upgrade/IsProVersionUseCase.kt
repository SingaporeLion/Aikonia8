package com.aikonia.app.domain.use_case.upgrade

import com.aikonia.app.domain.repository.PreferenceRepository
import javax.inject.Inject

class IsProVersionUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    operator fun invoke() = preferenceRepository.isProVersion()
}
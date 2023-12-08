package com.aikonia.app.domain.use_case.conversation

import com.aikonia.app.domain.repository.ConversationRepository
import javax.inject.Inject

class DeleteAllConversationUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository
) {
    suspend operator fun invoke() =
        conversationRepository.deleteAllConversation()
}
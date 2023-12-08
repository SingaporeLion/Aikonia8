package com.aikonia.app.domain.use_case.conversation

import com.aikonia.app.data.model.ConversationModel
import com.aikonia.app.domain.repository.ConversationRepository
import javax.inject.Inject

class CreateConversationUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository
) {
    suspend operator fun invoke(conversation: ConversationModel) =
        conversationRepository.addConversation(conversation)
}
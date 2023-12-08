package com.aikonia.app.domain.use_case.message

import com.aikonia.app.data.model.MessageModel
import com.aikonia.app.domain.repository.MessageRepository
import javax.inject.Inject

class CreateMessagesUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(message: MessageModel) =
        messageRepository.addMessage(message)
}
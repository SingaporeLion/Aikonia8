package com.aikonia.app.domain.repository

import com.aikonia.app.data.model.MessageModel

interface MessageRepository {
    suspend fun getMessages(conversationId: String): List<MessageModel>
    suspend fun addMessage(message: MessageModel)
    suspend fun deleteMessages(conversationId: String)
}
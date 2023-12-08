package com.aikonia.app.domain.repository

import com.aikonia.app.data.model.ConversationModel

interface ConversationRepository {
    suspend fun getConversations(): MutableList<ConversationModel>
    suspend fun addConversation(conversation: ConversationModel)
    suspend fun deleteConversation(conversationId: String)
    suspend fun deleteAllConversation()
}
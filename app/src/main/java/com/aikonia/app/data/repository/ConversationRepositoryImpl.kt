package com.aikonia.app.data.repository

import com.aikonia.app.data.model.ConversationModel
import com.aikonia.app.data.source.local.ConversAIDao
import com.aikonia.app.domain.repository.ConversationRepository
import javax.inject.Inject


class ConversationRepositoryImpl @Inject constructor(
    private val conversAIDao: ConversAIDao

) : ConversationRepository {
    override suspend fun getConversations(): MutableList<ConversationModel> =
        conversAIDao.getConversations()

    override suspend fun addConversation(conversation: ConversationModel) =
        conversAIDao.addConversation(conversation)

    override suspend fun deleteConversation(conversationId: String) {
        conversAIDao.deleteConversation(conversationId)
        conversAIDao.deleteMessages(conversationId)
    }


    override suspend fun deleteAllConversation() = conversAIDao.deleteAllConversation()

}
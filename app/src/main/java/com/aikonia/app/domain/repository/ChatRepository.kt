package com.aikonia.app.domain.repository

import com.aikonia.app.data.model.TextCompletionsParam
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun textCompletionsWithStream(scope: CoroutineScope, params: TextCompletionsParam): Flow<String>
}
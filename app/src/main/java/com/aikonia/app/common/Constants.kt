package com.aikonia.app.common

object Constants {
    const val BASE_URL = "https://api.openai.com/v1/"
    const val API_KEY = "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"

    const val REWARDED_AD_UNIT_ID =
        "ca-app-pub-2182457367918105/7878846240"

    const val PRIVACY_POLICY = "Place your privacy policy link here"
    const val ABOUT = "Place your About link here"
    const val HELP =  "Place your help link here"

    const val PRODUCT_ID = "chatai_pro"

    const val WEEKLY_BASE_PLAN = "conversai-pro"
    const val MONTHLY_BASE_PLAN = "conversai-pro-month"
    const val YEARLY_BASE_PLAN = "conversai-pro-year"

    const val MATCH_RESULT_STRING = "\"text\":"
    const val MATCH_RESULT_TURBO_STRING = "\"content\":"

    const val TRANSITION_ANIMATION_DURATION = 400
    const val IS_DELETE = "is_delete"


    object Preferences {
        const val LANGUAGE_CODE = "languageCode"
        const val LANGUAGE_NAME = "languageName"
        const val SHARED_PREF_NAME = "mova_shared_pref"
        const val DARK_MODE = "darkMode"
        const val PRO_VERSION = "proVersion"
        const val FIRST_TIME = "firstTime"
        const val FREE_MESSAGE_COUNT = "freeMessageCount"
        const val FREE_MESSAGE_LAST_CHECKED_TIME = "freeMessageLastCheckedTime"
        const val FREE_MESSAGE_COUNT_DEFAULT = 300
        const val INCREASE_MESSAGE_COUNT = 1
    }

    object Queries {
        const val GET_CONVERSATIONS = "SELECT * FROM conversations ORDER BY createdAt DESC"
        const val DELETE_CONVERSATION = "DELETE FROM conversations WHERE id = :id"
        const val DELETE_ALL_CONVERSATION = "DELETE FROM conversations"
        const val DELETE_MESSAGES = "DELETE FROM messages WHERE conversationId = :conversationId"
        const val GET_MESSAGES =
            "SELECT * FROM messages WHERE conversationId = :conversationId ORDER BY createdAt DESC"

    }

    object Firebase {
        const val CONVERSATION_COLLECTION: String = "conversations";
        const val MESSAGE_COLLECTION: String = "messages";
    }

    object Endpoints {
        const val TEXT_COMPLETIONS = "completions"
        const val TEXT_COMPLETIONS_TURBO = "chat/completions"
    }

    const val DEFAULT_AI =
        "Du bist die 10-jährige Lynn aus Aikonia, wie in deinem Fine Tuning Modell (ft:gpt-3.5-turbo-1106:personal::8JRC1Idj) beschrieben und trainiert wurde."


}
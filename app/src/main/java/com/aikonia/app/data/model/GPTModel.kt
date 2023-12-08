package com.aikonia.app.data.model


enum class GPTModel(val model: String, val maxTokens: Int) {
    gpt35Turbo("ft:gpt-3.5-turbo-1106:personal::8JRC1Idj", 4000),
}
package com.aikonia.app.data.source.remote

import com.google.gson.JsonObject
import com.aikonia.app.common.Constants
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Streaming

interface ConversAIService {

    @POST(Constants.Endpoints.TEXT_COMPLETIONS)
    @Streaming
    fun textCompletionsWithStream(@Body body: JsonObject): Call<ResponseBody>

    @POST(Constants.Endpoints.TEXT_COMPLETIONS_TURBO)
    @Streaming
    fun textCompletionsTurboWithStream(@Body body: JsonObject): Call<ResponseBody>

    @POST(Constants.Endpoints.TEXT_COMPLETIONS_TURBO)
    @Streaming
    fun sendGreeting(@Body body: JsonObject): Call<ResponseBody>
}
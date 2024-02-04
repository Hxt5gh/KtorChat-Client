package com.hxt5gh.frontend.data.remote.message

import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    val chatId: String,
    val message: List<Message>
)
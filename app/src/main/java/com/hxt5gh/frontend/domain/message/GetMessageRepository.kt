package com.hxt5gh.frontend.domain.message

import com.hxt5gh.frontend.data.remote.message.Message

interface GetMessageRepository {

    suspend fun getMessageList(chatId : String) : List<Message>

}
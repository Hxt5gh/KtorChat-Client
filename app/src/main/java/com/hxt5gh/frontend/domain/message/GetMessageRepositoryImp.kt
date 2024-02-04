package com.hxt5gh.frontend.domain.message

import com.hxt5gh.frontend.data.remote.message.Message
import com.hxt5gh.frontend.data.remote.message.MessageServicesImp
import javax.inject.Inject

class GetMessageRepositoryImp @Inject constructor(private val  messageServices: MessageServicesImp): GetMessageRepository {

    override suspend fun getMessageList(chatId: String): List<Message> {
        return messageServices.getAllMessages(chatId).message
    }
}
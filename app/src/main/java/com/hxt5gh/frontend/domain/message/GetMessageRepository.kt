package com.hxt5gh.frontend.domain.message

import com.hxt5gh.frontend.data.remote.message.Message
import com.hxt5gh.frontend.data.remote.userDetail.ChatInfo
import com.hxt5gh.frontend.data.remote.userDetail.Response
import kotlinx.coroutines.flow.Flow

interface GetMessageRepository {

    suspend fun getMessageList(chatId : String) : List<Message>

    suspend fun getSearchedUser(query : String) : Flow<List<Response>>

    suspend fun userUChatWith(chatId : String) : ChatInfo

    suspend fun deleteUserChat(userId: String, chatId: String)  : Boolean

}
package com.hxt5gh.frontend.data.remote.userDetail

import kotlinx.coroutines.flow.Flow

interface UserNameSearchService {
    suspend fun userNameSearchService(query : String ) : Flow<List<Response>>

    suspend fun userUChatWith(chatId : String) : ChatInfo

    suspend fun deleteUserChat(userId : String , chatId: String ) : Boolean
}
package com.hxt5gh.frontend.data.remote.socket

import com.hxt5gh.frontend.Resource
import com.hxt5gh.frontend.data.remote.message.Message
import com.hxt5gh.frontend.data.remote.message.MessageReceive
import kotlinx.coroutines.flow.Flow

interface ChatSocketService {

    //when we open the app we join to the server and on server inside online hashmap
    suspend fun initSession(userId : String) : Resource<Unit>

    suspend fun sendMessages(message: Message)

    fun messageObservable() : Flow<MessageReceive>

    //when we close the app we will close the session
    suspend fun closeSession()
}
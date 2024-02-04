package com.hxt5gh.frontend.data.remote.message

interface MessageServices {

    suspend fun getAllMessages(chatId : String) : MessageDto


//    "http://127.0.0.1:8080/getMessages?id=12"

}
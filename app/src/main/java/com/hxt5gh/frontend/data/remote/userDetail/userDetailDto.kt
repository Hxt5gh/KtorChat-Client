package com.hxt5gh.frontend.data.remote.userDetail

import kotlinx.serialization.Serializable


@Serializable
data class UserDataDto(
    val userId : String ,
    val userName : String,
    val userChats : List<ChatInfo> = emptyList()
)
@Serializable
data class ChatInfo(
    val chatId : String,
    val sender : String,
    val receiver : String
)
/*
{
    "userId": "harpreetSingh",
    "userName": "harpreetSingh"
}
*/

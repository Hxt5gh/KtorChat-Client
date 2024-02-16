package com.hxt5gh.frontend.data.remote.userDetail

import kotlinx.serialization.Serializable


@Serializable
data class UserDataDto(
    val userId : String ,
    val userName : String,
    val displayName : String,
    val profileUri : String? = null,
)

@Serializable
data class ChatInfo(
    val userId : String ,//google id
    val chatList : List<ChatDetail> = emptyList()

)
@Serializable
data class ChatDetail(
    val chatId : String, //user chat id
    val sender : String,
    val receiver : String,
    val receiverName: String? = null,
    val receiverPic: String? = null,
    val lastMessage : String ? = null,
    val timeStamp : Long? = null
)

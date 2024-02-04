package com.hxt5gh.frontend.data.remote.message

import kotlinx.serialization.Serializable


@Serializable
data class Message(
//    val id: String,
    val message: String,
    val recipientId: String,
    val senderId: String,
    val timeStamp: Long
)

@Serializable
data class MessageReceive(
    val id: String ,
    val senderId : String,
    val recipientId: String,
    val message : String ,
    val timeStamp : Long
)
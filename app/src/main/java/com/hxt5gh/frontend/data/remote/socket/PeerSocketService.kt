package com.hxt5gh.frontend.data.remote.socket

import com.hxt5gh.frontend.Resource
import com.hxt5gh.frontend.data.remote.message.MessageReceive
import com.hxt5gh.frontend.data.remote.message.PeerRes
import kotlinx.coroutines.flow.Flow

interface PeerSocketService {

    suspend fun initSocket(userId : String) : Resource<Unit>

    fun messageObservable() : Flow<PeerRes>

    suspend fun closeSocket()


    suspend fun isSocketActive() : Boolean
}
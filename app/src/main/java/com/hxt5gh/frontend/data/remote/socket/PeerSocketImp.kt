package com.hxt5gh.frontend.data.remote.socket

import android.util.Log
import com.hxt5gh.frontend.BuildConfig
import com.hxt5gh.frontend.Resource
import com.hxt5gh.frontend.data.remote.message.MessageReceive
import com.hxt5gh.frontend.data.remote.message.PeerRes
import io.ktor.client.HttpClient
import io.ktor.client.features.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.http.URLBuilder
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.WebSocketSession
import io.ktor.http.cio.websocket.close
import io.ktor.http.cio.websocket.readText
import io.ktor.http.takeFrom
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONException

class PeerSocketImp(private val httpClient: HttpClient) : PeerSocketService {

    private var  socket : WebSocketSession? = null

    override suspend fun initSocket(userId: String): Resource<Unit> {
        val url = URLBuilder().apply {
            takeFrom("ws://${BuildConfig.KTOR_IP_ADDRESS_Two}")
            path("get-peer")
            parameters.append("userId" , userId)
        }.build()

        return  try {
            socket = httpClient.webSocketSession {
                url(url)
            }
            if (socket?.isActive == true)
            {
                Log.d("TAG", "Peer socket Connected ${socket?.isActive}")
                Resource.Success(Unit)
            }else
            {
                Log.d("TAG", "Peer socket Not Connected")
                Resource.Error("Couldn't estabish Connection connection")
            }
        }catch (e : Exception){
            Log.d("TAG", "Peer socket Not Connected")
            e.printStackTrace()
            Resource.Error(e.localizedMessage ?: "Unknown Error")
        }
    }

    override fun messageObservable(): Flow<PeerRes> = flow {
        socket?.incoming?.consumeEach { frame ->
            if (frame is Frame.Text) {
                val json = frame.readText()
                try {
                    val message = Json.decodeFromString<PeerRes>(json)
                    emit(message)
                } catch (e: JSONException) {
                    Log.e("TAG", "Error decoding message JSON: ${e.message}")
                } catch (e: SerializationException) {
                    Log.e("TAG", "Serialization error: ${e.message}")
                }
            }
        }
    }

    override suspend fun closeSocket() {
        socket?.close()
    }

    override suspend fun isSocketActive(): Boolean {
        return socket?.isActive == true
    }
}
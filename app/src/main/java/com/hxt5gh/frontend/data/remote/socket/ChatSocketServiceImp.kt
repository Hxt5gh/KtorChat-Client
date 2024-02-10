package com.hxt5gh.frontend.data.remote.socket

import android.util.Log
import com.hxt5gh.frontend.BuildConfig
import com.hxt5gh.frontend.Resource
import com.hxt5gh.frontend.data.remote.message.Message
import com.hxt5gh.frontend.data.remote.message.MessageReceive
import com.hxt5gh.frontend.data.remote.message.MessageServices
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONException
import kotlin.math.log

class ChatSocketServiceImp(private val httpClient: HttpClient) : ChatSocketService {

    private var  socket : WebSocketSession? = null

    override suspend fun initSession(userId: String): Resource<Unit> {
        val url = URLBuilder().apply {
            takeFrom("ws://${BuildConfig.KTOR_IP_ADDRESS_Two}")
            path("chat-socket")
            parameters.append("userId" , userId)
        }.build()

       return  try {
             socket = httpClient.webSocketSession {
                url(url)
            }
            if (socket?.isActive == true)
            {
                Log.d("TAG", "socket Connected")
//                socket?.incoming?.consumeEach {
//                    val json = (it as? Frame.Text)?.readText() ?: ""
//                    Log.d("TAG", "Received JSON: $json")
//                    try {
//                        val message = Json.decodeFromString<MessageReceive>(json)
//                        Log.d("TAG", "Decoded message: $message")
//                    } catch (e: Exception) {
//                        Log.e("TAG", "Error decoding message JSON: ${e.message}")
//                    }
//                }
                Resource.Success(Unit)
            }else
            {
                Log.d("TAG", "socket Not Connected")
                Resource.Error("Couldn't estabish Connection connection")
            }
        }catch (e : Exception){
            Log.d("TAG", "socket Not Connected")
            e.printStackTrace()
            Resource.Error(e.localizedMessage ?: "Unknown Error")
        }
    }

    override suspend fun sendMessages(message: Message) {
        try {
            socket?.send(Frame.Text(Json.encodeToString(Message.serializer() , message)))
        }catch (e : Exception){
                e.printStackTrace()
        }

    }


   override fun messageObservable(): Flow<MessageReceive> = flow {
        socket?.incoming?.consumeEach { frame ->
            if (frame is Frame.Text) {
                val json = frame.readText()
                Log.d("TAG", "Received JSON: $json")
                try {
                    val message = Json.decodeFromString<MessageReceive>(json)
                    emit(message)
                    Log.d("TAG", "Decoded message: $message")
                } catch (e: JSONException) {
                    Log.e("TAG", "Error decoding message JSON: ${e.message}")
                } catch (e: SerializationException) {
                    Log.e("TAG", "Serialization error: ${e.message}")
                }
            }
        }
    }




    override suspend fun closeSession() {
       socket?.close()
    }

    override suspend fun isSocketActive(): Boolean {
        return socket?.isActive == true
    }
}
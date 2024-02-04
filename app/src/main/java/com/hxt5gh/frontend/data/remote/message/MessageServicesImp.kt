package com.hxt5gh.frontend.data.remote.message

import android.util.Log
import com.hxt5gh.frontend.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom
import javax.inject.Inject

class MessageServicesImp @Inject constructor(val httpClient: HttpClient) : MessageServices {


    override suspend fun getAllMessages(chatId : String): MessageDto {
      return  try {
            val url = URLBuilder().apply {
                takeFrom("http://${BuildConfig.KTOR_IP_ADDRESS_Two}")
                path("getMessages")
                parameters.append("id", chatId)
            }.build()

            Log.d("TAG", "getAllMessages: url is -> ${url}")
            httpClient.get<MessageDto>(url)
        } catch (e: Exception) {
            // Handle other exceptions (e.g., network issues)
            Log.d("TAG", "getAllMessages: Not GETTING DATA ${e.message}")
            e.printStackTrace()
          MessageDto("Not Getting Data returning Empty List", emptyList())
        }
    }
}
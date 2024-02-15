package com.hxt5gh.frontend.data.remote.userDetail

import android.util.Log
import com.hxt5gh.frontend.BuildConfig
import com.hxt5gh.frontend.data.remote.message.MessageDto
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.features.get
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.request
import io.ktor.client.statement.response
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.ParametersBuilder
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import kotlin.math.log

class UserNameSearchServiceImp @Inject constructor(private val httpClient: HttpClient) : UserNameSearchService {
    override suspend fun userNameSearchService(query: String): Flow<List<Response>> {
        return try {

            val url = URLBuilder().apply {
                takeFrom("http://${BuildConfig.KTOR_IP_ADDRESS_Two}")
                path("search-user")
            }.build()

            val response: HttpResponse = httpClient.request(url) {
                method = HttpMethod.Get
                parameter("userName" , query )
            }
            val data: List<Response> = response.receive()
            Log.d("debug", "userNameSearchService: Query is ${query} ")

            // 'data' will contain the list received from the server

            data.forEach {
                Log.d("debug", "userNameSearchService: ${it.userId} ${it.userName} ${it.displayName} ${it.profileUri} ")
            }



            flow<List<Response>> {
                emit(data)
            }
        } catch (e : Exception){
            Log.d("debug", "saveUserDetail: ${e.message}")
            e.printStackTrace()
            flow<List<Response>> {emit(emptyList())}
        }
    }
}

@Serializable
data class Response(
    val userId : String = "",
    val userName : String = "",
    val displayName : String = "",
    val profileUri : String = "",
)
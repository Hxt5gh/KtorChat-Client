package com.hxt5gh.frontend.data.remote.userDetail

import android.util.Log
import com.hxt5gh.frontend.BuildConfig
import com.hxt5gh.frontend.data.remote.message.MessageServices
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequest
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom
import javax.inject.Inject

class UserDetailServiceImp @Inject constructor(val httpClient: HttpClient) : UserDetailService {
    override suspend fun saveUserDetail(user: UserDataDto): Boolean {
      return try {
          val url = URLBuilder().apply {
              takeFrom("http://${BuildConfig.KTOR_IP_ADDRESS_Two}")
              path("save-user")
          }.build()

          val response: HttpResponse = httpClient.request(url) {
              method = HttpMethod.Post
              body = user
          }

          val statusCode = response.status.value
          statusCode == HttpStatusCode.OK.value

      } catch (e : Exception){
          Log.d("debug", "saveUserDetail: ${e.message}")
          e.printStackTrace()
          return false
      }
    }
}
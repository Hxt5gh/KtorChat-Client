package com.hxt5gh.frontend.data.remote.userDetail

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.hxt5gh.frontend.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Calendar
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class UserDetailServiceImp @Inject constructor(val httpClient: HttpClient) : UserDetailService {

        private val firebaseStorage: FirebaseStorage = FirebaseStorage.getInstance()
         private val storageReference: StorageReference = firebaseStorage.getReference("pics")
         private val calendar : Calendar = Calendar.getInstance()

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



    override suspend fun saveImageToFirebase(uri : Uri)  : String {

        var imageUri : String = ""

        return suspendCancellableCoroutine {cont->
            storageReference.child(FirebaseAuth.getInstance().uid!!).putFile(uri)
                .addOnSuccessListener {
                    storageReference.child(FirebaseAuth.getInstance().uid!!).downloadUrl.addOnSuccessListener { uri ->
                        imageUri = uri.toString()
                        cont.resume(imageUri)
                    }
                }
                .addOnFailureListener{
                    cont.resumeWithException(it)
                }

            cont.invokeOnCancellation { /* deregister your onSuccess and onFailure listeners here */ }

        }
    }

    override suspend fun getUserById(userId: String) : UserDataDto {
        return try {
            val url = URLBuilder().apply {
                takeFrom("http://${BuildConfig.KTOR_IP_ADDRESS_Two}")
                path("get-user")
            }.build()

            val response: HttpResponse = httpClient.request(url) {
                method = HttpMethod.Get
                parameter("userid" , userId )
            }

            val data : UserDataDto = response.receive()
            Log.d("debug", "getUserById: ${data.userId} ${data.userName} ${data.displayName} ${data.profileUri}")

           data
        } catch (e : Exception){
            Log.d("debug", "saveUserDetail: ${e.message}")
            e.printStackTrace()
            UserDataDto("" ,"" ,"","")
        }
    }
}
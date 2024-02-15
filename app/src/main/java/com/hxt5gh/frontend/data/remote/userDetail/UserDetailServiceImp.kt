package com.hxt5gh.frontend.data.remote.userDetail

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.hxt5gh.frontend.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom
import java.util.Calendar
import javax.inject.Inject

class UserDetailServiceImp @Inject constructor(val httpClient: HttpClient) : UserDetailService {

        private val firebaseStorage: FirebaseStorage = FirebaseStorage.getInstance()
         private val storageReference: StorageReference = firebaseStorage.getReference("Profile_Pic")
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


    override suspend fun saveImageToFirebase(uri : Uri)  : String? {

        var imageUri : String? = null

        Log.d("debug", "uploadImage")

            storageReference.child("${calendar.timeInMillis}").putFile(uri)
                .addOnSuccessListener {
                    storageReference.child(FirebaseAuth.getInstance().uid!!).downloadUrl.addOnSuccessListener { uri ->
                        imageUri = uri.toString()
                        Log.d("debug", "uploadImage:  ${uri}")
                    }
                }
                .addOnFailureListener{
                    it.printStackTrace()
                    Log.d("debug", "uploadImage:  ${it.message}")
                }

        return imageUri

    }

}
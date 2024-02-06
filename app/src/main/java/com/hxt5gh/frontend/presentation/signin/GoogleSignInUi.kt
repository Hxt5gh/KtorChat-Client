package com.hxt5gh.frontend.presentation.signin

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import com.hxt5gh.frontend.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.hxt5gh.frontend.BuildConfig
import com.hxt5gh.frontend.data.remote.userDetail.UserDataDto
import com.hxt5gh.frontend.data.remote.userDetail.UserDetailServiceImp
import com.hxt5gh.frontend.domain.userDetailRepo.SaveUserRepositoryImp
import com.onesignal.OneSignal
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.math.log


class GoogleSignInUi(
    val contxt : Context,
    val signInClint : SignInClient,
    val saveUserRepository: SaveUserRepositoryImp
) {

    private  val auth = Firebase.auth

    suspend fun signIn() : IntentSender?
    {
        val result = try {
            signInClint.beginSignIn(signInBuilder()).await()
        } catch (e : Exception){
            e.printStackTrace()
            Log.d("main", "signIn:aaa ${e.message}")
            if (e is CancellationException) throw e
            null
        }

        return  result?.pendingIntent?.intentSender
    }


    suspend fun signInWithIntent(intent : Intent) : SignInResult{
        val credential =  signInClint.getSignInCredentialFromIntent(intent)
//        val idToken = credential.googleIdToken
//        val useName = credential.id
//        val password = credential.password
        val googleIdToken = credential.googleIdToken

        Log.d("main", "signInWithIntent: ${googleIdToken}")

        val googleCredential =  GoogleAuthProvider.getCredential(googleIdToken , null)

        return try {

            val user = auth.signInWithCredential(googleCredential).await().user
            //saving data to databse
            if (user != null) {
                saveUserRepository.saveUserDetail(
                    UserDataDto(
                        userId = user.uid,
                        userName = credential.displayName
                    )
                )
            }
            //OneSignal
            val externalId = auth.uid // You will supply the external user id to the OneSignal SDK
            OneSignal.login(externalId.toString())
            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        userName = displayName,
                        profilePic = photoUrl?.toString()
                    )
                },
                errorMessage = null
            )

        }catch (e :ApiException)
        {
            e.printStackTrace()
            Log.d("main", "signIn: no googel account  ${e.message}")
            if (e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMessage = null
            )

        }
    }

    suspend fun signOut(){
        try {
            signInClint.signOut().await()
            auth.signOut()
        }catch (e : ApiException)
        {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    fun getSignInUser() :UserData? = auth.currentUser?.run {
        UserData(
            userId = uid,
            userName = displayName,
            profilePic = photoUrl?.toString()
        )
    }

    private fun signInBuilder() : BeginSignInRequest
    {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(BuildConfig.WEB_CLINT_ID)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}


data class SignInResult(
    val data : UserData?,
    val errorMessage : String?
)

data class UserData(
    val userId : String,
    val userName :String?,
    val profilePic : String?
)

//suspend fun signIn(): IntentSender? {
//    return try {
//        val task = signInClint.beginSignIn(signInBuilder())
//
//        suspendCancellableCoroutine { continuation ->
//            task.addOnCompleteListener { result ->
//                if (result.isSuccessful) {
//                    val pendingIntent = result.result?.pendingIntent
//                    val intentSender = pendingIntent?.intentSender
//                    continuation.resume(intentSender)
//                } else {
//                    // Handle the failure case
//                    val exception = result.exception
//                    exception?.printStackTrace()
//                    Log.d("main", "signIn: ${exception?.message}")
//                    continuation.resume(null)
//                }
//            }
//
//            // Handle cancellation by canceling the task
//            continuation.invokeOnCancellation {
//                //task.result.pendingIntent.cancel()
//            }
//        }
//    } catch (e: Exception) {
//        e.printStackTrace()
//        Log.d("main", "signIn: ${e.message}")
//        if (e is CancellationException) throw e
//        null
//    }
//}
//
//private fun signInBuilder() : BeginSignInRequest
//{
//    return BeginSignInRequest.Builder()
//        .setGoogleIdTokenRequestOptions(
//            GoogleIdTokenRequestOptions.builder()
//                .setSupported(true)
//                .setFilterByAuthorizedAccounts(false)
//                .setServerClientId(contxt.getString(R.string.clint_id))
//                .build()
//        )
//        .setAutoSelectEnabled(true)
//        .build()
//}
//
//
//suspend fun signInWithIntent(intent : Intent) : SignInResult{
//    val credential =  signInClint.getSignInCredentialFromIntent(intent)
//    val googleIdToken = credential.googleIdToken
//    Log.d("main", "signInWithIntent: token ${googleIdToken}")
//    return try {
//        SignInResult(
//            data =
//            UserData(
//                token = googleIdToken
//            ),
//            errorMessage = null
//        )
//
//    }catch (e :ApiException)
//    {
//        e.printStackTrace()
//        Log.d("main", "signIn: ${e.message}")
//        if (e is CancellationException) throw e
//        SignInResult(
//            data = null,
//            errorMessage = null
//        )
//    }
//}
//
//fun signOut(){
//    signInClint.signOut()
//}
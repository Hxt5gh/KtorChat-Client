package com.hxt5gh.frontend

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModelStore
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.hxt5gh.frontend.presentation.signin.SignInViewModel
import com.onesignal.OneSignal
import com.onesignal.OneSignal.User
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@HiltAndroidApp
class AppApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        OneSignal.initWithContext(this , ONE_SIGNAL_APP_ID)

        CoroutineScope(Dispatchers.IO).launch {
            OneSignal.Notifications.requestPermission(true)

            val id =  User.pushSubscription.id
            val id1 = User.onesignalId
            val id2 = User.externalId
            Log.d("TAG", "pushSubscription ID: ${id}")
            Log.d("TAG", "onesignalId : ${id1} ")
            Log.d("TAG", "externalId  : ${id2}")
        }

    }

    companion object{
        const val ONE_SIGNAL_APP_ID = "970f2905-34f2-4905-8d93-5c60b4c5fc55"
    }
}
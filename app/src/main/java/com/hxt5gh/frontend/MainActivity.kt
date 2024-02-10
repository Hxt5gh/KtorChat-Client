package com.hxt5gh.frontend

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStore
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.hxt5gh.frontend.data.remote.socket.ChatSocketServiceImp
import com.hxt5gh.frontend.presentation.chat.SocketViewModel
import com.hxt5gh.frontend.presentation.signin.SignInViewModel

import com.hxt5gh.frontend.ui.routes.RootNavigationGraph
import com.hxt5gh.frontend.ui.theme.FrontEndTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var socketServiceImp: ChatSocketServiceImp

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FrontEndTheme {

//                val signInViewModel: SignInViewModel = hiltViewModel()
//                val socketViewModel: SocketViewModel = hiltViewModel()
//                val auth = Firebase.auth
//                if (signInViewModel.isAuthenticated(applicationContext))
//                {
//                    socketViewModel.init(auth.uid.toString())
//                }

                val navController = rememberNavController()

                Log.d("debug", "onCreate: towardRood")
                RootNavigationGraph(navController = navController)


            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        runBlocking {
            socketServiceImp.closeSession()
        }
    }
}


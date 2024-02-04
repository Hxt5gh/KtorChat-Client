package com.hxt5gh.frontend.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.hxt5gh.frontend.presentation.signin.GoogleSignInUi
import com.hxt5gh.frontend.ui.routes.Routes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay

@Composable
fun splashScreen(googleSignInUi: GoogleSignInUi , onRouteClick :(route : String) -> Unit) {




    LaunchedEffect(key1 = Unit){
        delay(1900)
        if(googleSignInUi.getSignInUser() != null){

            onRouteClick(Routes.Main_Screen)
        }
        else
        {
            onRouteClick(Routes.GOOGLE_BUTTON_SCREEN)
        }
    }


    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Splash Screen", fontSize = 32.sp)
        }
    }

}


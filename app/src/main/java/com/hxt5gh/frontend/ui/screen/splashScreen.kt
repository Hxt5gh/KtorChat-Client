package com.hxt5gh.frontend.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.hxt5gh.frontend.R
import com.hxt5gh.frontend.presentation.signin.GoogleSignInUi
import com.hxt5gh.frontend.presentation.signin.SignInViewModel
import com.hxt5gh.frontend.ui.routes.AuthScreen
import com.hxt5gh.frontend.ui.routes.Graph
import com.hxt5gh.frontend.ui.routes.Routes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun splashScreen(navController: NavHostController) {

    val context = LocalContext.current

    val viewModel : SignInViewModel = hiltViewModel()




    val list = listOf(
        R.drawable.a0,
        R.drawable.a1,
        R.drawable.a2,
        R.drawable.a3,
        R.drawable.a4,
        R.drawable.a5,
        R.drawable.a6,
        R.drawable.a7,
        R.drawable.a8,
        R.drawable.a9,
        R.drawable.a10,
        R.drawable.a11,
        R.drawable.a12,
        R.drawable.a13,
        R.drawable.a14,
        R.drawable.a16,
        R.drawable.a17,
        R.drawable.a18,
        R.drawable.a19,
        R.drawable.a20,
        R.drawable.a21,
        R.drawable.a22,
        R.drawable.a23,
        R.drawable.a24,
        R.drawable.a25,
        R.drawable.a26,
        R.drawable.a27,
    )

    LaunchedEffect(key1 = Unit){
        delay(1900)//1900
        if(viewModel.isAuthenticated(context)){
            //home
            navController.navigate(Graph.MAIN_SCREEN_PAGE){
                popUpTo(AuthScreen.SPLASH.route)
                {
                    inclusive = true
                }
            }
        }
        else
        {
            //login
            navController.navigate(AuthScreen.SIGNUP.route) {
                popUpTo(AuthScreen.SPLASH.route)
                {
                    inclusive = true
                }
            }
        }
    }


    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
           Text(text = "Splash Screen",
               fontSize = 32.sp,
               textAlign = TextAlign.Center)
        }
    }

}

@Composable
fun ContinuousImageChange(imageList: List<Int>, interval: Long) {
    var currentIndex by remember { mutableStateOf(0) }
    var coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = currentIndex) {
        while (true) {
            delay(interval)
            coroutineScope.launch {
                currentIndex = (currentIndex + 1) % imageList.size
            }
        }
    }

    Image(
        painter =painterResource(id = imageList[currentIndex]),
        contentDescription = null,
        modifier = Modifier.fillMaxSize()
    )
}



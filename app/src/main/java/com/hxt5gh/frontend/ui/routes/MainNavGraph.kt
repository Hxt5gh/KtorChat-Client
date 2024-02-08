package com.hxt5gh.frontend.ui.routes

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStore
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.android.gms.auth.api.identity.Identity
import com.hxt5gh.frontend.presentation.signin.GoogleSignInUi
import com.hxt5gh.frontend.presentation.signin.SignInViewModel
import com.hxt5gh.frontend.ui.screen.ChatScreen
import com.hxt5gh.frontend.ui.screen.ProfileScreen
import com.hxt5gh.frontend.ui.screen.SearchScreen

@Composable
fun MainNavGraph(navController: NavHostController , onRoute : (String) -> Unit) {

    NavHost(
        navController = navController,
        startDestination = Routes.Chat_Screen
    ){
        Log.d("debug", "MainNavGraph: level 8")

        composable(route = Routes.Chat_Screen){
            ChatScreen(navController)
        }
        composable(route = Routes.Search_Screen){
            SearchScreen(navController)
        }
        composable(route = Routes.Profile_Screen){
            ProfileScreen(navController){
                onRoute(it)
            }
        }

    }
}
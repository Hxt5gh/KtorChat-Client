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
import com.hxt5gh.frontend.ui.screen.User

@Composable
fun MainNavGraph(navController: NavHostController , onRoute : (String) -> Unit , onClick : (User) -> Unit) {

    NavHost(
        navController = navController,
        startDestination = Routes.Chat_Screen
    ){


        composable(route = Routes.Chat_Screen){
            ChatScreen(
                navController ,
                onClick = {
                    onClick(it)
                },
                onRoute = {
                    onRoute(it)
                }
            )
        }
        composable(route = Routes.Search_Screen){
            SearchScreen(navController , onClick ={
                Log.d("debug", "MainNavGraph: 3 -> ${it.id} ${it.name} ${it.profileImage}")
                onClick(
                    User(
                        id = it.id,
                        name = it.name,
                        profileImage = it.profileImage,
                        lastMessage = "",
                        timestamp = 0
                    )
                )
            })
        }
        composable(route = Routes.Profile_Screen){
            ProfileScreen(navController){
                onRoute(it)
            }
        }

    }
}
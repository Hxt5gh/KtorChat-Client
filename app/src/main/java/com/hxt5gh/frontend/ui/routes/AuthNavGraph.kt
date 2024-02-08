package com.hxt5gh.frontend.ui.routes

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hxt5gh.frontend.presentation.signin.GoogleSignInUi
import com.hxt5gh.frontend.presentation.signin.SignInState
import com.hxt5gh.frontend.presentation.signin.SignInViewModel
import com.hxt5gh.frontend.ui.screen.SignInScreen
import com.hxt5gh.frontend.ui.screen.splashScreen


fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    state: SignInState
) {

    //auth graph nested its route is auth
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.SPLASH.route
    ){

        composable(route = AuthScreen.SPLASH.route){
            //splash if success navigate onRouteClick
           splashScreen(navController = navController )
        }
        composable(route = AuthScreen.SIGNUP.route){
            SignInScreen(state = state ,navController)
        }

    }


}


sealed class AuthScreen(val route : String)
{
    object SPLASH : AuthScreen("SPLASH")
    object SIGNUP : AuthScreen("SIGNUP")
    object LOGIN : AuthScreen("LOGIN")
}
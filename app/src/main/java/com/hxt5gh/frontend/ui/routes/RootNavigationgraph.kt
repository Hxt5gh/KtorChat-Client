package com.hxt5gh.frontend.ui.routes

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hxt5gh.frontend.presentation.signin.SignInViewModel
import com.hxt5gh.frontend.ui.screen.DetailScreen
import com.hxt5gh.frontend.ui.screen.SignInScreen
import com.hxt5gh.frontend.ui.screen.splashScreen
import com.hxt5gh.frontend.R

@Composable
fun RootNavigationGraph(navController: NavHostController) {



    val viewmodel : SignInViewModel= hiltViewModel()
    val state by viewmodel.state.collectAsState()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = AuthScreen.SPLASH.route)
    {

        composable(route = AuthScreen.SPLASH.route){
            //splash if success navigate onRouteClick
            splashScreen(navController = navController)

        }
        composable(route = AuthScreen.SIGNUP.route){
            SignInScreen(state = state ,navController)
        }


        composable(
            route = Graph.MAIN_SCREEN_PAGE
        ){
             MainScreen(
                 onRoute = {
                 navController.navigate(it){
                     popUpTo(Graph.MAIN_SCREEN_PAGE){
                         inclusive = true
                     }

                 } },
                 onClick = {
                     val userId = it.id
                     val name = it.name
                     val resource = it.profileImage
                     //toDetail
                     navController.navigate("${Graph.DETAILS}/${"userId"}/${name}/${resource.toInt()}")
                 })
        }
        composable(route = "${Graph.DETAILS}/{${"id"}}/{${"name"}}/{${"res"}}" ,
            arguments = listOf(
            navArgument(name = "id"){type = NavType.StringType},
            navArgument(name = "name"){type = NavType.StringType},
            navArgument(name = "res"){type = NavType.IntType}
        )){
            val id = it.arguments?.getString("id").toString()
            val name = it.arguments?.getString("name").toString()
            val res = it.arguments?.getInt("res")

            DetailScreen(id , name , res!! ){
                navController.navigate(Graph.MAIN_SCREEN_PAGE){
                    popUpTo(Graph.MAIN_SCREEN_PAGE){
                        inclusive = true
                    }

                }
            }
        }
    }

}


//"${Routes.UPDATE_SCREEN_ROUTE}/{${"id"}}"

//"${Routes.UPDATE_SCREEN_ROUTE}/${events.item.id}"



object Graph{
    const val ROOT = "root-graph"
    const val AUTHENTICATION = "authentication_graph"
    const val MAIN_SCREEN_PAGE = "main_screen_page_graph"
    const val DETAILS = "detail_graph"

}
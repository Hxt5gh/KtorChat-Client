package com.hxt5gh.frontend.ui.routes

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.hxt5gh.frontend.R
import com.hxt5gh.frontend.presentation.message.GetMessagesViewModel
import com.hxt5gh.frontend.presentation.signin.GoogleSignInUi
import com.hxt5gh.frontend.presentation.signin.SignInViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onRoute : (String) -> Unit) {
    /*
    val signInViewModel : SignInViewModel = hiltViewModel()
    val auth = Firebase.auth

    val context = LocalContext.current
    val googleSignInUiClient by lazy {
        GoogleSignInUi(
            contxt = context,
            signInClint = Identity.getSignInClient(context),
            signInViewModel.saveUserRepository
        )
    }
    val data = googleSignInUiClient.getSignInUser()

     LaunchedEffect(Unit){
         signInViewModel.init(auth.uid.toString())
     }

     */

    val navController = rememberNavController()
    val navList = listOf(
        BottomNavigationIcon(
            title = "Chats",
            selectedIcon = R.drawable.baseline_home_24,
            unSelectedIcon = R.drawable.outline_home_24,
            hasNews = false,
            badgeCounter = null,
            route = Routes.Chat_Screen
        ),
        BottomNavigationIcon(
            title = "FindOne",
            selectedIcon = R.drawable.baseline_explore_24,
            unSelectedIcon = R.drawable.outline_explore_24,
            hasNews = false,
            route = Routes.Search_Screen
        ),
        BottomNavigationIcon(
            title = "Profile",
            selectedIcon = R.drawable.baseline_profile_24,
            unSelectedIcon = R.drawable.outline_person_24,
            hasNews = false,
            route = Routes.Profile_Screen
        )
    )
    var selectItemState by remember {
        mutableStateOf(0)
    }
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    Scaffold(
      //  bottomBar = { BottomBar(navController) }
//        /*
        bottomBar = { NavigationBar {
                navList.forEachIndexed { index, bottomNavigationIcon ->
                    NavigationBarItem(
                        label = { Text(text = bottomNavigationIcon.title.toString()) },
//                        selected =  selectItemState == index,
                        selected =  currentDestination?.hierarchy?.any {
                                                                       it.route == bottomNavigationIcon.route
                        } == true,
                        onClick = {
                            selectItemState = index
                            navController.navigate(bottomNavigationIcon.route){
                                popUpTo(navController.graph.findStartDestination().id){
                                    saveState = true
                                }
                                restoreState = true
                            }
                        },
                        icon = {
                            BadgedBox(
                                badge ={
                                    if(bottomNavigationIcon.badgeCounter != null)
                                    {
                                        Badge {
                                            Text(text = bottomNavigationIcon.badgeCounter.toString())
                                        }
                                    }else if(bottomNavigationIcon.hasNews){
                                        Badge()
                                    }
                                }) {
                                Icon(
                                    painter = painterResource(
                                        if ( currentDestination?.hierarchy?.any {
                                                it.route == bottomNavigationIcon.route
                                            } == true)
                                        {
                                            bottomNavigationIcon.selectedIcon
                                        }else{
                                            //unselected
                                            bottomNavigationIcon.unSelectedIcon
                                        }
                                    ),
                                    contentDescription = bottomNavigationIcon.title
                                )
                            }
                        }
                    )
                }
            }
        }

//         */
    ) {
        Surface(modifier = Modifier.padding(it)) {
            MainNavGraph(navController = navController){
                onRoute(it)
            }
        }

    }
}

@Composable
fun BottomBar(navController: NavController) {
    Log.d("debug", "MainScreen: level 2")

    val navList = listOf(
        BottomNavigationIcon(
            title = "Chats",
            selectedIcon = R.drawable.baseline_home_24,
            unSelectedIcon = R.drawable.outline_home_24,
            hasNews = false,
            badgeCounter = null,
            route = Routes.Chat_Screen
        ),
        BottomNavigationIcon(
            title = "FindOne",
            selectedIcon = R.drawable.baseline_explore_24,
            unSelectedIcon = R.drawable.outline_explore_24,
            hasNews = false,
            route = Routes.Search_Screen
        ),
        BottomNavigationIcon(
            title = "Profile",
            selectedIcon = R.drawable.baseline_profile_24,
            unSelectedIcon = R.drawable.outline_person_24,
            hasNews = false,
            route = Routes.Profile_Screen
        )
    )


    Log.d("debug", "MainScreen: level 3")

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Log.d("debug", "MainScreen: level 4")

    Log.d("debug", "BottomBar: ${navBackStackEntry != null}")


    Log.d("debug", "MainScreen: level 5")
    val currentDestination = navBackStackEntry?.destination
    Log.d("debug", "MainScreen: level 6")


    val bottomDestination = navList.all { it.route  == currentDestination?.route }

    if (bottomDestination){
        NavigationBar {
            navList.forEach{screen ->
                AddItems(
                    screen ,
                    currentDestination,
                    navController
                )

            }
        }
    }



}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItems(screen: BottomNavigationIcon, currentDestination: NavDestination?, navController: NavController) {
    NavigationBar {
            NavigationBarItem(
                label = { Text(text = screen.title.toString()) },
                selected =  currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route){
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                },
                icon = {
                    BadgedBox(
                        badge ={
                            if(screen.badgeCounter != null)
                            {
                                Badge {
                                    Text(text = screen.badgeCounter.toString())
                                }
                            }else if(screen.hasNews){
                                Badge()
                            }
                        }) {
                        Icon(
                            painter = painterResource(screen.selectedIcon),
                            contentDescription = null
                        )
                    }
                })

    }
}

data class BottomNavigationIcon(
    val title : String,
    val selectedIcon : Int,
    val unSelectedIcon : Int,
    val hasNews : Boolean,
    val badgeCounter : Int? = null,
    val route  : String

)

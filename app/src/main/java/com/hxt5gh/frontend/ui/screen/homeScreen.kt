package com.hxt5gh.frontend.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hxt5gh.frontend.R
import com.hxt5gh.frontend.presentation.signin.GoogleSignInUi
import com.hxt5gh.frontend.ui.routes.Routes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(googleSignInUi: GoogleSignInUi ,navController: NavHostController, onClick : () -> Unit) {



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

    var selectItemState by rememberSaveable { mutableStateOf(0) }

                   Scaffold(
//                       topBar = {
//                           TopAppBar(
//                               title = { Text(text = "Compose") },
//                               colors = TopAppBarDefaults.smallTopAppBarColors(
//                                   containerColor = MaterialTheme.colorScheme.primary ,
//                                   titleContentColor = MaterialTheme.colorScheme.onPrimary
//                               )
//                           )
//                       },
                       bottomBar = {
                           NavigationBar {
                               navList.forEachIndexed { index, bottomNavigationIcon ->
                                   NavigationBarItem(
                                       label = { Text(text = bottomNavigationIcon.title.toString()) },
                                       selected =  selectItemState == index,
                                       onClick = {
                                           selectItemState = index
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
                                                       if (index == selectItemState)
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
                                       })
                               }
                           }
                       }
                   ) {padding ->
                       Surface(
                           modifier = Modifier
                               .fillMaxSize()
                               .padding(padding)
                       ) {
                           if (selectItemState == 0)
                           {
                             ChatScreen()
                           }else if (selectItemState == 1)
                           {
                              SearchScreen()
                           }else if (selectItemState == 2)
                           {
                              ProfileScreen(googleSignInUi) {
                                    onClick()
                              }
                           }


                       }
                   }
}


@Preview(showBackground = true)
@Composable
fun prev() {
   // HomeScreen( rememberNavController(), onClick = {})
}

data class BottomNavigationIcon(
    val title : String,
    val selectedIcon : Int,
    val unSelectedIcon : Int,
    val hasNews : Boolean,
    val badgeCounter : Int? = null,
    val route  : String

)
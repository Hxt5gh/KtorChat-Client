package com.hxt5gh.frontend.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.hxt5gh.frontend.presentation.chat.GetMessagesViewModel
import com.hxt5gh.frontend.presentation.chat.SocketViewModel
import com.hxt5gh.frontend.presentation.signin.SignInViewModel
import com.hxt5gh.frontend.ui.ChatViewItem
import com.hxt5gh.frontend.ui.routes.AuthScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavHostController , onClick :(User) -> Unit) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Find One") },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary

                ),
                actions = {
                    var expandState by remember { mutableStateOf(false) }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                   IconButton(onClick = { expandState = true }) {
                       Icon(
                           imageVector = Icons.Filled.Menu,
                           contentDescription = null,
                           modifier = Modifier.size(24.dp),
                           tint = MaterialTheme.colorScheme.onPrimary
                       )
                       DropdownMenu(
                           expanded = expandState,
                           onDismissRequest = { expandState = false }
                       ) {
                          DropdownMenuItem(
                              text = { Text(text = "LogOut") },
                              onClick = { /*navController.navigate(AuthScreen.SIGNUP.route) */ }
                          )
                       }
                   }
                }
            )
        }
    ) {padding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            color = MaterialTheme.colorScheme.background
        ) {
            val dummyUsers = listOf(
                User(
                    id = "useridagain",
                    name = "John Doe",
                    profileImage = "",
                    lastMessage = "Hey, how's it going?",
                    timestamp = System.currentTimeMillis() - 60000 // 1 minute ago
                ))
            Column() {
                LazyColumn(){
                    itemsIndexed(dummyUsers){index, item ->
                        ChatViewItem(image = item.profileImage, name = item.name , lastMessage = item.lastMessage ){
                            //onClick
                            onClick(item)
                        }
                    }
                }
            }

        }

    }


}

@Preview(showBackground = true )
@Composable
fun ChatPrev() {
    ChatScreen(navController = rememberNavController() , onClick = {})
}

data class User(
    val id: String,
    val name: String,
    val profileImage: String,
    val lastMessage: String,
    val timestamp: Long
)
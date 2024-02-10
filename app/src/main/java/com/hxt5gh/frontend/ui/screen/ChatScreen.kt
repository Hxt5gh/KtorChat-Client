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
                    id = "",
                    name = "John Doe",
                    profileImage = com.hxt5gh.frontend.R.drawable.th4,
                    lastMessage = "Hey, how's it going?",
                    timestamp = System.currentTimeMillis() - 60000 // 1 minute ago
                ),
                User(
                    id = "",
                    name = "Jane Smith",
                    profileImage =  com.hxt5gh.frontend.R.drawable.th5,
                    lastMessage = "What are you up to?",
                    timestamp = System.currentTimeMillis() - 120000 // 2 minutes ago
                ),
                User(
                    id = "",
                    name = "Mike Johnson",
                    profileImage =  com.hxt5gh.frontend.R.drawable.th7,
                    lastMessage = "Let's catch up soon!",
                    timestamp = System.currentTimeMillis() - 180000 // 3 minutes ago
                ),
                User(
                    id = "",
                    name = "Emily Brown",
                    profileImage = com.hxt5gh.frontend.R.drawable.th,
                    lastMessage = "How was your day?",
                    timestamp = System.currentTimeMillis() - 240000 // 4 minutes ago
                ),
                User(
                    id = "",
                    name = "Chris Wilson",
                    profileImage =  com.hxt5gh.frontend.R.drawable.th2,
                    lastMessage = "Do you have any plans this weekend?",
                    timestamp = System.currentTimeMillis() - 300000 // 5 minutes ago
                ),
                User(
                    id = "",
                    name = "Sarah Davis",
                    profileImage = com.hxt5gh.frontend.R.drawable.th7,
                    lastMessage = "I'm excited about the project!",
                    timestamp = System.currentTimeMillis() - 360000 // 6 minutes ago
                ),
                User(
                    id = "",
                    name = "Michael Taylor",
                    profileImage = com.hxt5gh.frontend.R.drawable.th5,
                    lastMessage = "Let's grab lunch tomorrow.",
                    timestamp = System.currentTimeMillis() - 420000 // 7 minutes ago
                ),
                User(
                    id = "",
                    name = "Emma Martinez",
                    profileImage = com.hxt5gh.frontend.R.drawable.th6,
                    lastMessage = "Can you send me the file?",
                    timestamp = System.currentTimeMillis() - 480000 // 8 minutes ago
                ),
                User(
                    id = "",
                    name = "Daniel Anderson",
                    profileImage =  com.hxt5gh.frontend.R.drawable.th8,
                    lastMessage = "I'll be late for the meeting.",
                    timestamp = System.currentTimeMillis() - 540000 // 9 minutes ago
                ),
                User(
                    id = "",
                    name = "Olivia Garcia",
                    profileImage = com.hxt5gh.frontend.R.drawable.th6,
                    lastMessage = "Thanks for your help!",
                    timestamp = System.currentTimeMillis() - 600000 // 10 minutes ago
                ),
                User(
                    id = "",
                    name = "James Rodriguez",
                    profileImage = com.hxt5gh.frontend.R.drawable.th1,
                    lastMessage = "Let's meet up this evening.",
                    timestamp = System.currentTimeMillis() - 660000 // 11 minutes ago
                ),
                User(
                    id = "",
                    name = "Sophia Hernandez",
                    profileImage = com.hxt5gh.frontend.R.drawable.th5,
                    lastMessage = "Have you seen the latest episode?",
                    timestamp = System.currentTimeMillis() - 720000 // 12 minutes ago
                ),
                User(
                    id = "",
                    name = "William Lopez",
                    profileImage = com.hxt5gh.frontend.R.drawable.th4,
                    lastMessage = "I'll be out of town next week.",
                    timestamp = System.currentTimeMillis() - 780000 // 13 minutes ago
                )
            )
            Column {
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
    val profileImage: Int,
    val lastMessage: String,
    val timestamp: Long
)
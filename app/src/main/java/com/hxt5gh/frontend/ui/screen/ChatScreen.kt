package com.hxt5gh.frontend.ui.screen

import android.util.Log
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
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.hxt5gh.frontend.data.remote.userDetail.ChatDetail
import com.hxt5gh.frontend.data.remote.userDetail.ChatInfo
import com.hxt5gh.frontend.presentation.chat.ChatScreenViewModel
import com.hxt5gh.frontend.presentation.chat.GetMessagesViewModel
import com.hxt5gh.frontend.presentation.chat.SocketViewModel
import com.hxt5gh.frontend.presentation.signin.GoogleSignInUi
import com.hxt5gh.frontend.presentation.signin.SignInViewModel
import com.hxt5gh.frontend.ui.ChatViewItem
import com.hxt5gh.frontend.ui.routes.AuthScreen
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavHostController , onClick :(User) -> Unit ,  onRoute: (String) -> Unit) {

    val chatScreenViewModel: ChatScreenViewModel = hiltViewModel()
    val viewModel: SignInViewModel = hiltViewModel()
    val saveUserRepository = viewModel.saveUserRepository
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val googleSignInUiClient by lazy {
        GoogleSignInUi(
            contxt = context,
            signInClint = Identity.getSignInClient(context),
            saveUserRepository
        )
    }

    var isDialog by remember {
        mutableStateOf(false)
    }

    var deletename by remember {
        mutableStateOf("")
    }
    var recId by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit){
        Log.d("debug", "ChatScreen: ${chatScreenViewModel.chatInfo}")
    }


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
                              onClick = {
                                  //logout here
                                  scope.launch {
                                  googleSignInUiClient.signOut()
                                  }
                                  onRoute(AuthScreen.SIGNUP.route)
                               // navController.navigate(AuthScreen.SIGNUP.route)
                              }
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
                    itemsIndexed(chatScreenViewModel.chatInfo.chatList){index, item ->
                        Log.d("debug", "ChatScreen: Checking -> ${item}>")
                       // val lastMessage = getMessagesViewModel.getMessage("${Firebase.auth.uid}${item.receiver}")
                        ChatViewItem(
                            image = item.receiverPic.toString(),
                            name = item.receiverName.toString(),
                            lastMessage = item.lastMessage.toString(),
                            time = item.timeStamp?.toLong(),
                            onClickk = {
                                onClick(
                                    User(
                                        id = item.receiver,
                                        name = item.receiverName.toString(),
                                        profileImage = item.receiverPic.toString(),
                                        lastMessage = "",
                                        timestamp = 0
                                    )
                                )
                            },
                            onLongg = {
                                Log.d("debug", "ChatScreen: Log Clicked")
                                isDialog = true
                                deletename = item.receiverName.toString()
                                recId = item.receiver
                            }

                        )
                    }
                }
                if (isDialog){
                    AlertDialog(
                       // title = {Text(text = "")},
                       // text = {Text(text = "Are you sure you want to delete chat with ${deletename}")},
                        text = {
                            Text(text =
                            buildAnnotatedString {
                                append("Are you sure you want to delete chat with")
                                pushStyle(SpanStyle(color = Color.Red))
                                append(" ${deletename}")
                                toAnnotatedString()
                            }
                            )
                        },
                        onDismissRequest = {
                            isDialog = !isDialog
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    //delete chat
                                    val chatid = "${Firebase.auth.uid.toString()}${recId}"
                                    Log.d("debug", "ChatScreen: DELETEID ${chatid} ")
                                    val res =  chatScreenViewModel.deleteUserChat(
                                      userId = Firebase.auth.uid.toString(),
                                        chatId = chatid
                                     )
//                                    chatScreenViewModel.deleteUserChat(
//                                        userId = Firebase.auth.uid.toString(),
//                                        chatId = "${Firebase.auth.uid.toString()}${"Y"}"
//                                    )
                                    isDialog = !isDialog
                                }
                            ) {
                                Text("Yes")
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    isDialog = !isDialog
                                }
                            ) {
                                Text("No")
                            }
                        }
                    )
                }
            }

        }

    }


}

@Preview(showBackground = true)
@Composable
fun ChatPrev() {
    ChatScreen(navController = rememberNavController() , onClick = {} , onRoute = {})
}

data class User(
    val id: String,
    val name: String,
    val profileImage: String,
    val lastMessage: String,
    val timestamp: Long
)
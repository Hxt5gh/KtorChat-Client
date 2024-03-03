package com.hxt5gh.frontend.ui.screen

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.hxt5gh.frontend.presentation.chat.SearchScreenViewModel
import com.hxt5gh.frontend.presentation.chat.SocketViewModel
import com.hxt5gh.frontend.ui.SearchUserView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController, onClick: (User) -> Unit) {

    var no by remember {
        mutableStateOf("")
    }
    var isSearching by remember {
        mutableStateOf(false)
    }
    var textState by remember { mutableStateOf("") }
    var enable by remember { mutableStateOf(false) }
    var items = remember { mutableStateListOf("harry", "james", "potter", "lily") }
    val searchScreenViewModel: SearchScreenViewModel = hiltViewModel()
    var closeConnectionJob: Job? = null


    val auth = Firebase.auth
    val socketViewModel: SocketViewModel = hiltViewModel()
    var messagePeer by remember { mutableStateOf("") }

    LaunchedEffect(key1 = true , socketViewModel.peerID){
        socketViewModel.peerID.collect{
            messagePeer = it
            Log.d("TAG", "SearchScreen: peer id receved  ${messagePeer}")
        }
    }

    Scaffold(
        /*
       topBar = {

           Row {
               Spacer(modifier = Modifier.size(8.dp))
               Card(
                   modifier = Modifier
                       .fillMaxWidth()
                       .height(60.dp)
                       .clip(CutCornerShape(0.dp))
                       .padding(start = 18.dp, end = 18.dp)
               ) {
                   UserSearchInput(
                       value = textState,
                       onTextChange = {
                           textState = it
                       }
                   )
               }

           }
       }
         */
    ) {
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            query = textState,
            onQueryChange = {
                textState = it
                if (textState.length >= 1) {
                    searchScreenViewModel.getSearchedUser(textState)
                }
            },
            onSearch = {
                enable = false
            },
            active = enable,
            onActiveChange = {
                enable = it
            },
            placeholder = { Text(text = "Search") },
            leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "null") },
            trailingIcon = {
                if (enable) {
                    Icon(
                        modifier = Modifier.clickable
                        {
                            if (textState.isNotEmpty()) {
                                textState = ""
                            } else {
                                enable = false
                            }
                        },
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "null"
                    )
                }

            }
        ) {
            searchScreenViewModel.userList.forEach {

                SearchUserView(
                    image = it.profileUri,
                    userId = it.userId,
                    userName = it.userName,
                    displayName = it.displayName
                ) {
                    val userId = it.id
                    val displayName = it.name
                    val pic = it.profileImage

                    onClick(
                        User(
                            id = userId,
                            name = displayName,
                            profileImage = pic,
                            lastMessage = "",
                            timestamp = 0
                        )
                    )
                }

            }
        }
        Surface(
            modifier = Modifier
                .padding(it)
                .background(MaterialTheme.colorScheme.background),
        ) {


            Box(modifier = Modifier.fillMaxSize().padding(bottom = 220.dp), contentAlignment = Alignment.BottomCenter) {
                val col = MaterialTheme.colorScheme.primary
                Text(
                    text = if (isSearching){"Searching..."}else{"Search"},
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.
                        drawBehind {
                            drawCircle(
                                color = col,
                                radius = 220f
                            )
                        }
                        .clickable(!isSearching) {
                            isSearching = !isSearching
                            socketViewModel.initPeer(auth.uid.toString())
                            // Start a coroutine to automatically close the connection after 5 seconds
                            closeConnectionJob = CoroutineScope(Dispatchers.IO).launch {
                                for (i in 5 downTo 0) {
                                    delay(1000)
                                    no = i.toString()
                                }
                                isSearching = !isSearching
                                closeConnectionJob?.cancel() // Cancel any existing job
                                socketViewModel.closePeer()
                            }

                        }
                )

            }

        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchScreenPrev() {
    SearchScreen(navController = rememberNavController(), onClick = {})
}

data class UserInfo(
    val userid: String,
    val displayName: String,
    val profileImage: String?,
)
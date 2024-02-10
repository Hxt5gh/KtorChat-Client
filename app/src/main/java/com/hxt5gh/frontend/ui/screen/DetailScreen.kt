package com.hxt5gh.frontend.ui.screen

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.hxt5gh.frontend.R
import com.hxt5gh.frontend.data.remote.message.Message
import com.hxt5gh.frontend.presentation.chat.DetailScreenViewModel
import com.hxt5gh.frontend.presentation.chat.GetMessagesViewModel
import com.hxt5gh.frontend.presentation.chat.SocketViewModel
import com.hxt5gh.frontend.presentation.signin.SignInViewModel
import com.hxt5gh.frontend.ui.TextChatInput
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    userId : String ,
    userName : String ,
    resource : Int = R.drawable.a1,
    onClick: () ->Unit
) {

    var textState by remember { mutableStateOf("")}
    val auth = Firebase.auth
    val socketViewModel : SocketViewModel = hiltViewModel()
    val scope = rememberCoroutineScope()


    var message by remember { mutableStateOf("") }

    LaunchedEffect(key1 = true){
        if (socketViewModel.isActive()){
            socketViewModel.messageObservable().collect{msg->
                message = msg.message
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        Image(
                            painter = painterResource(id = resource),
                            contentDescription = "",
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = userName,
                            color = MaterialTheme.colorScheme.onPrimary,
                            maxLines = 1
                        )
                    } },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                       Icon(
                           imageVector = Icons.Filled.ArrowBack,
                           contentDescription = "",
                           tint = MaterialTheme.colorScheme.onPrimary,
                           modifier = Modifier
                               .padding()
                               .clickable {
                                   //navigate back
                                   onClick()
                               }
                       )
                }
            )
        }
    ) {padding->
        Surface(
            modifier = Modifier
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .weight(.9f)
                        .fillMaxWidth()
                ){//for messages

                    LazyColumn() {
                        item {
                             Text(text = "New Message -> ${message}" , fontSize = 32.sp )
                        }
                        item {
                             Text(text = "New Message -> ${message}" , fontSize = 32.sp )
                        }

                        item {
                             Text(text = "New Message -> ${message}" , fontSize = 32.sp )
                        }
                        item {
                             Text(text = "New Message -> ${message}" , fontSize = 32.sp )
                        }
                        item {
                             Text(text = "New Message -> ${message}" , fontSize = 32.sp )
                        }
                        item {
                             Text(text = "New Message -> ${message}" , fontSize = 32.sp )
                        }
                        item {
                             Text(text = "New Message -> ${message}" , fontSize = 32.sp )
                        }
                        item {
                             Text(text = "New Message -> ${message}" , fontSize = 32.sp )
                        }
                        item {
                             Text(text = "New Message -> ${message}" , fontSize = 32.sp )
                        }
                        item {
                             Text(text = "New Message -> ${message}" , fontSize = 32.sp )
                        }
                        item {
                             Text(text = "New Message -> ${message}" , fontSize = 32.sp )
                        }
                        item {
                             Text(text = "New Message -> ${message}" , fontSize = 32.sp )
                        }
                        item {
                             Text(text = "New Message -> ${message}" , fontSize = 32.sp )
                        }
                        item {
                             Text(text = "New Message -> ${message}" , fontSize = 32.sp )
                        }
                        item {
                             Text(text = "New Message -> ${message}" , fontSize = 32.sp )
                        }
                        item {
                             Text(text = "New Message -> ${message}" , fontSize = 32.sp )
                        }
                        item {
                             Text(text = "New Message -> ${message}" , fontSize = 32.sp )
                        }
                        item {
                             Text(text = "New Message -> ${message}" , fontSize = 32.sp )
                        }
                        item {
                             Text(text = "New Message -> ${message}" , fontSize = 32.sp )
                        }
                        item {
                             Text(text = "New Message -> ${message}" , fontSize = 32.sp )
                        }
                        item {
                             Text(text = "New Message -> ${message}" , fontSize = 32.sp )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .weight(.1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){

                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 8.dp, end = 8.dp, top = 3.dp, bottom = 3.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Card(
                            modifier = Modifier
                                .fillMaxWidth(.8f)
                                .fillMaxHeight(.8f)
                                .clip(RoundedCornerShape(20.dp))
                                .border(
                                    .5.dp, Color.Black,
                                    shape = RoundedCornerShape(20.dp)
                                )
                        ) {
                            TextChatInput(
                                value = textState,
                                onTextChange = {
                                    textState = it
                                }
                            )
                        }

                      //  Spacer(modifier = Modifier.size(8.dp))

                        IconButton(onClick = {
                            Log.d("debug", "DetailScreen: messsage Send -> ${textState}")
                            val msg = textState
                            scope.launch {

                                if (msg.length > 0)
                                {
                                    socketViewModel.sendMessage(
                                        Message(
                                            message = msg,
                                            senderId = auth.uid.toString(),
                                            recipientId = "Jaz5seHjL0Nct0IdSgixRW9wM8s2",
                                            timeStamp = System.currentTimeMillis()
                                        )
                                    )
                                }
                            }
                            textState = ""
                        }) {
                            Icon( imageVector = Icons.Default.Send,
                                contentDescription = "Send",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier
                                    .size(68.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary)
                                    .padding(
                                        start = 10.dp,
                                        end = 8.dp,
                                        top = 8.dp,
                                        bottom = 8.dp
                                    )
                            )
                        }
                    }

                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun DetailScreenPrev() {
    DetailScreen(userId = "" , userName = "Harry", resource = R.drawable.cap, onClick = {} )
}
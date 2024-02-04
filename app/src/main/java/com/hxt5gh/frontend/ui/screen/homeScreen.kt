package com.hxt5gh.frontend.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.hxt5gh.frontend.data.remote.message.Message
import com.hxt5gh.frontend.data.remote.socket.ChatSocketServiceImp
import com.hxt5gh.frontend.data.remote.userDetail.UserDataDto
import com.hxt5gh.frontend.presentation.message.GetMessagesViewModel
import com.hxt5gh.frontend.presentation.signin.SignInViewModel
import com.hxt5gh.frontend.presentation.signin.UserData
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import javax.inject.Inject
import kotlin.math.log

@Composable
fun HomeScreen(data : UserData? , onClick : () -> Unit) {

    val viewModel : GetMessagesViewModel = hiltViewModel()
    val signInViewModel : SignInViewModel = hiltViewModel()

    val message = signInViewModel.messages.collectAsState()

    val auth = Firebase.auth


    Column(modifier = Modifier.fillMaxSize() , verticalArrangement = Arrangement.Center , horizontalAlignment = Alignment.CenterHorizontally) {


        if (data?.profilePic != null)
        {
            AsyncImage(model = data.profilePic , contentDescription ="" , modifier = Modifier.size(150.dp) )

        }
        if (data?.userName != null)
        {
            Text(text = "usename : ${data.userName} and user id : ${data.userId}")
        }


        Button(onClick = { onClick() }) {
            Text(text = "LogQut")
        }

        Spacer(modifier = Modifier.size(20.dp))

        Button(onClick = {
            Log.d("TAG", "HomeScreen: message ${viewModel.getMessage("12")}")
        }) {
            Text(text = "getMessage")
        }
        Spacer(modifier = Modifier.size(20.dp))

        Button(onClick = {
                signInViewModel.init(auth.uid.toString())
        }) {
            Text(text = "connect")
        }

        Spacer(modifier = Modifier.size(20.dp))

        Button(onClick = {
            signInViewModel.sendMessage(
                Message(
                    message = "Test with Fake Star",
                    senderId = auth.uid.toString(),
                    recipientId = "james",
                    timeStamp = System.currentTimeMillis()
                )
            )
        }) {
            Text(text = "sendMessage")
        }
        Spacer(modifier = Modifier.size(10.dp))


        Text(text = "New Message -> ${message.value.message}" )



    }

}


@Preview(showBackground = true)
@Composable
fun prev() {
  //  HomeScreen(data = null,onClick = {})
}
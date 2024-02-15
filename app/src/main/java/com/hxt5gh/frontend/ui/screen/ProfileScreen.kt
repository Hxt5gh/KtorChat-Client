package com.hxt5gh.frontend.ui.screen

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.hxt5gh.frontend.data.remote.message.Message
import com.hxt5gh.frontend.presentation.chat.GetMessagesViewModel
import com.hxt5gh.frontend.presentation.chat.ProfileScreenViewModel
import com.hxt5gh.frontend.presentation.chat.SearchScreenViewModel
import com.hxt5gh.frontend.presentation.chat.SocketViewModel
import com.hxt5gh.frontend.presentation.signin.GoogleSignInUi
import com.hxt5gh.frontend.presentation.signin.SignInViewModel
import com.hxt5gh.frontend.ui.routes.AuthScreen
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(navController: NavHostController  , onRoute : (String) -> Unit) {



    val viewModel : GetMessagesViewModel = hiltViewModel()
    val signInViewModel : SignInViewModel = hiltViewModel()
    val socketViewModel : SocketViewModel = hiltViewModel()
    val profileScreenViewModel : ProfileScreenViewModel = hiltViewModel()

    val searchScreenViewModel : SearchScreenViewModel = hiltViewModel()

    val message = socketViewModel.messages.collectAsState()

    val auth = Firebase.auth
   // socketViewModel.init(auth.uid.toString())

    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val googleSignInUiClient by lazy {
        GoogleSignInUi(
            contxt = context,
            signInClint = Identity.getSignInClient(context),
            signInViewModel.saveUserRepository
        )
    }
    val data = googleSignInUiClient.getSignInUser()


    var selectedImage by remember {
        mutableStateOf<Uri?>(null)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult ={uri ->
            selectedImage = uri
            profileScreenViewModel.uploadImage(uri!!)

        }
    )



    Column(modifier = Modifier.fillMaxSize() , verticalArrangement = Arrangement.Center , horizontalAlignment = Alignment.CenterHorizontally) {


        if (data?.profilePic != null)
        {
            AsyncImage(model = data.profilePic , contentDescription ="" , modifier = Modifier.size(150.dp) )

        }
        if (data?.userName != null)
        {
            Text(text = "usename : ${data.userName} and user id : ${data.userId}")
        }


        Button(onClick = { scope.launch {
             auth.signOut()
             googleSignInUiClient.signOut()
             onRoute(AuthScreen.SIGNUP.route)
        }
        }) {
            Text(text = "LogQut")
        }

        Spacer(modifier = Modifier.size(20.dp))


        Button(onClick = {
            Log.d("debug", "HomeScreen: message ${viewModel.getMessage("12")}")
            /*
            Log.d("debug", "ProfileScreen: get searched user  ${searchScreenViewModel.getSearchedUser("h")}")
            searchScreenViewModel.userList.forEach {
                Log.d("debug", "ProfileScreen: ${it.userId} ${it.userName}")
            }

             */
        }) {
            Text(text = "getMessage")
        }
        Spacer(modifier = Modifier.size(20.dp))


        /*

        Button(onClick = {
                socketViewModel.init(auth.uid.toString())
        }) {
            Text(text = "connect")
        }



        Spacer(modifier = Modifier.size(20.dp))

        Button(onClick = {
            socketViewModel.sendMessage(
                Message(
                    message = "Test with Fake Star",
                    senderId = auth.uid.toString(),
                    recipientId = "Jaz5seHjL0Nct0IdSgixRW9wM8s2",
                    timeStamp = System.currentTimeMillis()
                )
            )
        }) {
            Text(text = "sendMessage")
        }
        Spacer(modifier = Modifier.size(10.dp))

         */


        Text(text = "New Message -> ${message.value.message}" )



        Log.d("debug", "ProfileScreen  image e ${selectedImage} ")
        AsyncImage(model = selectedImage , contentDescription ="" , modifier = Modifier.size(50.dp) )
        Button(onClick = {
            launcher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
            //saaving image  to firebase


        }) {
            Text(text = "Image")
        }


    }


}
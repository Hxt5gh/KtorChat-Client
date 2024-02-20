package com.hxt5gh.frontend.ui.screen

import android.content.res.Configuration
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ch.qos.logback.core.spi.LifeCycle
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.hxt5gh.frontend.data.remote.userDetail.UserDataDto
import com.hxt5gh.frontend.presentation.chat.ProfileScreenViewModel
import com.hxt5gh.frontend.presentation.chat.UiEvents
import com.hxt5gh.frontend.presentation.chat.UiState
import com.hxt5gh.frontend.ui.profileInputFieldView
import com.hxt5gh.frontend.ui.profilePicView
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlin.math.log10

@Composable
fun ProfileScreen(navController: NavHostController, onRoute: (String) -> Unit) {


    val profileScreenViewModel: ProfileScreenViewModel = hiltViewModel()


    val formState = profileScreenViewModel._userDetail

    LaunchedEffect(Unit){
        profileScreenViewModel.getUserById(Firebase.auth.uid.toString())
    }
    var selectedImage by remember {
        mutableStateOf<Uri?>(null)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            selectedImage = uri
            if (uri != null) {

                profileScreenViewModel.uploadImage(uri)
            }

        }
    )

    var isLoading by remember {
        mutableStateOf(false)
    }

    val snackbarHostState = remember { SnackbarHostState() }

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner.lifecycle){
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
            profileScreenViewModel.uiEvents.collect{event->
                when(event){
                    is UiEvents.IsLoading ->{
                        isLoading = event.isLoading
                    }
                    is UiEvents.OnSuccess ->{
                        snackbarHostState.showSnackbar("${event.message}")
                    }
                    is UiEvents.OnError ->{

                    }
                }

            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { padding ->
        Surface(
            modifier = Modifier.padding(padding),
            color = MaterialTheme.colorScheme.background
        ) {
            if (formState != null){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = {
                        launcher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }) {
                        Text(text = "iamge")
                    }
                    Spacer(modifier = Modifier.size(20.dp))
                    profilePicView(
                        image = if (selectedImage != null) selectedImage.toString() else formState.profileUri.toString(),
                        modifierc = Modifier
                            .size(150.dp)
                            .clip(CircleShape),
                        onClick = {

                            launcher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )

                        }
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    profileInputFieldView(
                        inputType = "User Name",
                        // inputValue = profileScreenViewModel.userDetail.userName,
                        inputValue = formState.userName.toString(),
                        onValueChange = {
                            profileScreenViewModel.onEvent(UiState.UserName(it))
                        }
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    profileInputFieldView(
                        inputType = "Display Name",
                        //inputValue =profileScreenViewModel.userDetail.displayName,
                        inputValue =formState.displayName.toString(),
                        onValueChange = {
                            profileScreenViewModel.onEvent(UiState.DisplayName(it))
                        }
                    )
                    Spacer(modifier = Modifier.size(25.dp))
                    if (isLoading){
                        CircularProgressIndicator()
                    }
                    Spacer(modifier = Modifier.size(25.dp))
                    Button(onClick = {
                        //submit
                        profileScreenViewModel.onEvent(UiState.OnClick)
                    }) {
                        Text(text = "${profileScreenViewModel.imageUri}")
                    }
                }
            }
        }
    }


}

@Preview(
    showBackground = true, showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun Prev() {
    ProfileScreen(navController = rememberNavController(), onRoute = {})
}
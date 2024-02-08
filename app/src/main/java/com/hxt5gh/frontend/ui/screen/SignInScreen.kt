package com.hxt5gh.frontend.ui.screen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.identity.Identity
import com.hxt5gh.frontend.R
import com.hxt5gh.frontend.domain.userDetailRepo.SaveUserRepositoryImp
import com.hxt5gh.frontend.presentation.signin.GoogleSignInUi
import com.hxt5gh.frontend.presentation.signin.LoadingStatus
import com.hxt5gh.frontend.presentation.signin.SignInState
import com.hxt5gh.frontend.presentation.signin.SignInViewModel
import com.hxt5gh.frontend.ui.routes.AuthScreen
import com.hxt5gh.frontend.ui.routes.Graph
import com.hxt5gh.frontend.ui.routes.Routes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@Composable
fun SignInScreen(
    state: SignInState,
    navController: NavHostController
) {


    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val viewModel : SignInViewModel = hiltViewModel()
    val saveUserRepository = viewModel.saveUserRepository

    val googleSignInUiClient by lazy {
        GoogleSignInUi(
            contxt = context,
            signInClint = Identity.getSignInClient(context),
            saveUserRepository
        )
    }

    LaunchedEffect(key1 = state.errorMessage) {
        Log.d("debug", "login error ${state.errorMessage}")
        state.errorMessage?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }

    LaunchedEffect(key1 = state) {
        Log.d("debug", "Success ${viewModel.state.value.isSuccessFull}")
        if (state.isSuccessFull) {
            // Set loading status to false when the operation completes
            viewModel.loadingStatus.value = LoadingStatus(false)
            //ifLogin Successful
            navController.navigate(Graph.MAIN_SCREEN_PAGE){
                popUpTo(AuthScreen.SPLASH.route)
                {
                    inclusive = true
                }
            }
            viewModel.resetState()
        }
    }






    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == ComponentActivity.RESULT_OK) {
                scope.launch {
                    //getting result here
                    Log.d("debug", "SignInScreen: Getting result here")
                    val signInResult = googleSignInUiClient.signInWithIntent(
                        intent = result.data ?: return@launch
                    )

                    Log.d("debug", "SignInScreen: Getting result here again ${signInResult.data?.userName} ${signInResult.data?.profilePic}")
                    viewModel.signInResult(signInResult , navController)
                }

            }
        }
    )



    Surface(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 150.dp)
        ) {

            if (viewModel.loadingStatus.value.isLoading){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color.Blue,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            Button(shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.BottomCenter),
                onClick = {
                    scope.launch {
                        signIn(context = context , launcher , scope , viewModel , googleSignInUiClient)
                    }
            }) {
                Row(horizontalArrangement = Arrangement.SpaceEvenly , verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 8.dp),
                        painter = painterResource(id = R.drawable.googel),
                        contentDescription = ""
                    )
                    Text(text = "With Google")
                }

            }

        }


    }
}
suspend fun signIn(
    context: Context,
    launcher: ActivityResultLauncher<IntentSenderRequest>,
    scope : CoroutineScope,
    viewModel: SignInViewModel,
    googleSignInUiClient : GoogleSignInUi
)
{
    scope.launch {
        // Set loading status to true before starting the operation
        viewModel.loadingStatus.value = LoadingStatus(true)

        val signInIntent = googleSignInUiClient.signIn()
        launcher.launch(
            IntentSenderRequest.Builder(
                signInIntent ?: return@launch
            ).build()
        )
    }
}




@Preview(showBackground = true)
@Composable
fun SignInScreenPrev() {
//    SignInScreen(state = SignInState() ) {
//
//    }
}
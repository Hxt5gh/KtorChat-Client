package com.hxt5gh.frontend

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.hxt5gh.frontend.data.remote.socket.ChatSocketServiceImp
import com.hxt5gh.frontend.domain.userDetailRepo.SaveUserRepositoryImp
import com.hxt5gh.frontend.presentation.signin.GoogleSignInUi
import com.hxt5gh.frontend.presentation.signin.LoadingStatus
import com.hxt5gh.frontend.ui.screen.SignInScreen
import com.hxt5gh.frontend.presentation.signin.SignInViewModel
import com.hxt5gh.frontend.ui.routes.Routes
import com.hxt5gh.frontend.ui.screen.HomeScreen
import com.hxt5gh.frontend.ui.screen.splashScreen
import com.hxt5gh.frontend.ui.theme.FrontEndTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

//import androidx.lifecycle.collectAsStateWithLifecycle

//compose cradential manager


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var saveUserRepository: SaveUserRepositoryImp
    @Inject
    lateinit var socketServiceImp: ChatSocketServiceImp

    val googleSignInUiClient by lazy {
        GoogleSignInUi(
            contxt = applicationContext,
            signInClint = Identity.getSignInClient(applicationContext),
            saveUserRepository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FrontEndTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val viewmodel = viewModel<SignInViewModel>()
                    val state by viewmodel.state.collectAsState()

                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.StartIntentSenderForResult(),
                        onResult = {result ->
                            if (result.resultCode == RESULT_OK){
                                lifecycleScope.launch {
                                    //getting result here
                                    val signInResult  = googleSignInUiClient.signInWithIntent(
                                        intent = result.data ?: return@launch
                                    )

                                    viewmodel.signInResult(signInResult)
                                }

                            }
                        }
                    )



                    val navController = rememberNavController()
                    LaunchedEffect(key1 = state.isSuccessFull) {
                        Log.d("main", "onCreate: ")
                        if(state.isSuccessFull) {
                            Toast.makeText(
                                applicationContext,
                                "Sign in successful",
                                Toast.LENGTH_LONG
                            ).show()
                            // Set loading status to false when the operation completes
                            viewmodel.loadingStatus.value = LoadingStatus(false)
                            navController.navigate(Routes.Main_Screen)
                            viewmodel.resetState()
                        }
                    }





                    NavHost(navController = navController, startDestination = Routes.Splash_Screen){

                        composable(route = "${Routes.Splash_Screen}"){
                            splashScreen(googleSignInUiClient , onRouteClick = {
                                navController.navigate(it)
                            })
                        }

                        composable(route = "${Routes.GOOGLE_BUTTON_SCREEN}"){
                            SignInScreen(
                                state = state ,
                                onSignInClick = {
                                    lifecycleScope.launch {
                                        // Set loading status to true before starting the operation
                                        viewmodel.loadingStatus.value = LoadingStatus(true)

                                        val signInIntent = googleSignInUiClient.signIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(signInIntent ?: return@launch).build()
                                        )
                                    }

                                }
                            )
                        }


                        composable(route = "${Routes.Main_Screen}"){
                           HomeScreen(googleSignInUiClient.getSignInUser() , onClick = {
                               lifecycleScope.launch {
                                   googleSignInUiClient.signOut()
                                   navController.navigate(Routes.GOOGLE_BUTTON_SCREEN)
                               }
                           })
                        }

                    }

                }
            }
        }
    }
}
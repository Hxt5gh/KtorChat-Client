package com.hxt5gh.frontend.presentation.signin

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.identity.Identity
import com.hxt5gh.frontend.Resource
import com.hxt5gh.frontend.data.remote.message.Message
import com.hxt5gh.frontend.data.remote.message.MessageReceive
import com.hxt5gh.frontend.data.remote.socket.ChatSocketServiceImp
import com.hxt5gh.frontend.domain.userDetailRepo.SaveUserRepositoryImp
import com.hxt5gh.frontend.ui.routes.AuthScreen
import com.hxt5gh.frontend.ui.routes.Graph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class SignInViewModel @Inject constructor(private val chatSocketService: ChatSocketServiceImp) : ViewModel() {


    @Inject
    lateinit var saveUserRepository: SaveUserRepositoryImp

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    var loadingStatus = mutableStateOf(LoadingStatus())

    fun signInResult(result : SignInResult , navController: NavHostController)
    {
        Log.d("debug", "signInResult: is result not equal null  ${result.data != null} ")
        _state.update {
            it.copy(
                isSuccessFull = result.data != null,
                errorMessage = result.errorMessage
            )
        }
        if (state.value.isSuccessFull){
            Log.d("debug", "signInResult: Login")
            navController.navigate(Graph.MAIN_SCREEN_PAGE){
                popUpTo(AuthScreen.SIGNUP.route){
                    inclusive = true
                }
            }
            resetState()
        }
    }

    fun isAuthenticated(context : Context) : Boolean
    {
        val googleSignInUiClient by lazy {
            GoogleSignInUi(
                contxt = context,
                signInClint = Identity.getSignInClient(context),
                saveUserRepository
            )
        }
        return googleSignInUiClient.getSignInUser() != null
    }


    fun resetState()
    {
        _state.update { SignInState() }
    }
}

data class LoadingStatus(val isLoading: Boolean = false)
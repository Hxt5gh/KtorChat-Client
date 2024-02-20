package com.hxt5gh.frontend.presentation.chat

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.hxt5gh.frontend.data.remote.userDetail.UserDataDto
import com.hxt5gh.frontend.domain.userDetailRepo.SaveUserRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume


@HiltViewModel
class ProfileScreenViewModel @Inject constructor(private val saveUserRepositoryImp: SaveUserRepositoryImp): ViewModel() {


    var imageUri : String? = null

    var _userDetail by mutableStateOf<UserDataDto?>(null)

    var checkImage : String?  = null


    private var _uiEvents = Channel<UiEvents>()
    val uiEvents  = _uiEvents.receiveAsFlow()

    fun uploadImage(uri  : Uri)
    {
      viewModelScope.launch {
            imageUri = saveUserRepositoryImp.saveImageToFirebase(uri)
        }
    }

    fun getUserById(userId : String)
    {
        viewModelScope.launch  {
            Log.d("debug", "before -> ${_userDetail}")
            _userDetail  = saveUserRepositoryImp.getUseById(userId)
            checkImage = _userDetail!!.profileUri
            Log.d("debug", "after -> ${_userDetail}")
        }
    }

    fun onEvent(event : UiState)
    {
      when(event){

          is UiState.UserName ->{
            _userDetail = _userDetail?.copy(
                userName = event.userName
            )
          }
          is UiState.DisplayName ->{
              _userDetail = _userDetail?.copy(
                  displayName = event.displayName
              )

          }
          is UiState.ProfilePic ->{
          }

          is UiState.OnClick ->{
                // processing showing loading
                viewModelScope.launch {
                    if (_userDetail?.userName == ""){
                        sendEvents(UiEvents.OnSuccess("Enter User Name"))
                        return@launch
                    }
                    if (_userDetail?.displayName == ""){
                        sendEvents(UiEvents.OnSuccess("Enter Name To Be Displayed"))
                        return@launch
                    }
                    sendEvents(UiEvents.IsLoading(true))
                    delay(5000)
                    println("debug inin  ->${_userDetail}")
                    sendEvents(UiEvents.IsLoading(false))
                    sendEvents(UiEvents.OnSuccess("DetailSaved Successfully"))

                }
          }
      }


    }

    fun sendEvents(events: UiEvents)
    {
        viewModelScope.launch {
        _uiEvents.send(element = events)
        }
    }

}

sealed class UiState
{
  data class UserName(val userName : String) : UiState()
  data class DisplayName(val displayName : String ) : UiState()
  data class ProfilePic( val image : String ) : UiState()
  object OnClick : UiState()
}

sealed class UiEvents{
  data class IsLoading(val isLoading : Boolean) : UiEvents()
  data class OnSuccess(val message  : String) : UiEvents()
  data class OnError(val error : String) : UiEvents()
}
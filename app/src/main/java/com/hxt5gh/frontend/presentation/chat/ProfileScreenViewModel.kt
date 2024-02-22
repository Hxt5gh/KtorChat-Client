package com.hxt5gh.frontend.presentation.chat

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hxt5gh.frontend.data.remote.userDetail.UserDataDto
import com.hxt5gh.frontend.domain.userDetailRepo.SaveUserRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ProfileScreenViewModel @Inject constructor(private val saveUserRepositoryImp: SaveUserRepositoryImp) :
    ViewModel() {


    var _userDetail by mutableStateOf<UserDataDto?>(null)

    var check: UserDataDto? = null


    private var _uiEvents = Channel<UiEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()


    fun uploadImage(uri: Uri) {
        viewModelScope.launch {
               val profileUri = saveUserRepositoryImp.saveImageToFirebase(uri)
               sendEvents(UiEvents.IsLoading(false))
               _userDetail = _userDetail?.copy(
                   profileUri = profileUri
               )
        }
    }

    fun getUserById(userId: String) {
        viewModelScope.launch {
            _userDetail = saveUserRepositoryImp.getUseById(userId)
            check = _userDetail
        }
    }


    fun onEvent(event: UiState) {
        when (event) {

            is UiState.UserName -> {
                _userDetail = _userDetail?.copy(
                    userName = event.userName
                )
            }

            is UiState.DisplayName -> {
                _userDetail = _userDetail?.copy(
                    displayName = event.displayName
                )

            }

            is UiState.ProfilePic -> {
                sendEvents(UiEvents.IsLoading(true))
                uploadImage(Uri.parse(event.image))
            }

            is UiState.OnClick -> {
                if (_userDetail == check){
                    sendEvents(UiEvents.OnSuccess("Nothing Changed"))
                    return
                }
                // processing showing loading
                viewModelScope.launch {
                    if (_userDetail?.userName == "") {
                        sendEvents(UiEvents.OnSuccess("Enter User Name"))
                        return@launch
                    }
                    if (_userDetail?.displayName == "") {
                        sendEvents(UiEvents.OnSuccess("Enter Name To Be Displayed"))
                        return@launch
                    }
                    sendEvents(UiEvents.IsLoading(true))

                    val res =  saveUserRepositoryImp.saveUserDetail(_userDetail!!)
                    sendEvents(UiEvents.IsLoading(false))
                    if (!res){
                        sendEvents(UiEvents.OnSuccess("Something Went wrong"))
                    }else
                    {
                    sendEvents(UiEvents.OnError("DetailSaved Successfully"))
                    }
                }
            }
        }


    }

    fun sendEvents(events: UiEvents) {
        viewModelScope.launch {
            _uiEvents.send(element = events)
        }
    }

}

sealed class UiState {
    data class UserName(val userName: String) : UiState()
    data class DisplayName(val displayName: String) : UiState()
    data class ProfilePic(val image: String) : UiState()
    object OnClick : UiState()
}

sealed class UiEvents {
    data class IsLoading(val isLoading: Boolean) : UiEvents()
    data class OnSuccess(val message: String) : UiEvents()
    data class OnError(val error: String) : UiEvents()
}
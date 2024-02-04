package com.hxt5gh.frontend.presentation.signin

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hxt5gh.frontend.Resource
import com.hxt5gh.frontend.data.remote.message.Message
import com.hxt5gh.frontend.data.remote.message.MessageReceive
import com.hxt5gh.frontend.data.remote.socket.ChatSocketServiceImp
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

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    var loadingStatus = mutableStateOf(LoadingStatus())

    private val _messages = MutableStateFlow<MessageReceive>(MessageReceive("","","","",0))
    val messages: StateFlow<MessageReceive> = _messages

    fun signInResult(result : SignInResult)
    {
        _state.update {
            it.copy(
                isSuccessFull = result.data != null,
                errorMessage = result.errorMessage
            )
        }
    }

//    init
//    {
//        Log.d("socket", "first: ")
//        // Launch a coroutine to collect messages from the chat socket service
//        viewModelScope.launch {
//            chatSocketService.messageObservable().collect { message ->
//                // Emit each incoming message to the shared flow
//                _messages.value = message
//
//            }
//        }
//    }

    fun init(chatId : String)
    {
        viewModelScope.launch {
          val result =   chatSocketService.initSession(chatId)
            when(result){
               is Resource.Success ->{
                   chatSocketService.messageObservable().collect{message ->
                       _messages.value = _messages.value.copy(
                           id = message.id,
                           senderId = message.senderId,
                           recipientId = message.recipientId,
                           message = message.message,
                           timeStamp = message.timeStamp
                       )

                   }
                }
                is Resource.Error ->{
                    Log.d("TAG", "init: ERROR IN CONNECTIONG TO SOCKET")
                }
            }
        }

    }

    fun sendMessage(message: Message)
    {
        viewModelScope.launch {
            chatSocketService.sendMessages(message)
        }
    }




    fun resetState()
    {
        _state.update { SignInState() }
    }
}

data class LoadingStatus(val isLoading: Boolean = false)
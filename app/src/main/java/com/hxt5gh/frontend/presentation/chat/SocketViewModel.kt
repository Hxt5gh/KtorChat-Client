package com.hxt5gh.frontend.presentation.chat

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hxt5gh.frontend.Resource
import com.hxt5gh.frontend.data.remote.message.Message
import com.hxt5gh.frontend.data.remote.message.MessageReceive
import com.hxt5gh.frontend.data.remote.message.PeerRes
import com.hxt5gh.frontend.data.remote.socket.ChatSocketServiceImp
import com.hxt5gh.frontend.data.remote.socket.PeerSocketImp
import com.hxt5gh.frontend.domain.userDetailRepo.SaveUserRepositoryImp
import com.hxt5gh.frontend.presentation.signin.LoadingStatus
import com.hxt5gh.frontend.presentation.signin.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class SocketViewModel @Inject constructor(private val chatSocketService: ChatSocketServiceImp , private val peerSocketImp: PeerSocketImp): ViewModel() {


    private val _messages = MutableStateFlow<MessageReceive>(MessageReceive("","","","",0))
    val messages: StateFlow<MessageReceive> = _messages


  var peerID = MutableStateFlow("")


    fun init(chatId : String)
    {
        viewModelScope.launch {
            val result =   chatSocketService.initSession(chatId)

            when(result){
                is Resource.Success ->{

                }
                is Resource.Error ->{
                    Log.d("TAG", "init: ERROR IN CONNECTIONG TO SOCKET")
                }
            }
        }
    }

    fun messageObservable() : Flow<MessageReceive>
    {
        return chatSocketService.messageObservable()
    }

    fun sendMessage(message: Message)
    {
        viewModelScope.launch {
            chatSocketService.sendMessages(message)
        }
    }

    fun isActive() : Boolean
    {
        var isActive = false
        viewModelScope.launch {
          isActive =  chatSocketService.isSocketActive()
        }
        return isActive
    }

    fun disconnect()
    {
        viewModelScope.launch {
            chatSocketService.closeSession()
        }
    }


    //for peer

    fun initPeer(chatId : String)
    {
        viewModelScope.launch {
            val result =   peerSocketImp.initSocket(chatId)

            when(result){
                is Resource.Success ->{
                    peerSocketImp.messageObservable().collect{
                        peerID.emit(it.userId)
                    }
                }
                is Resource.Error ->{
                    Log.d("TAG", "init: ERROR IN CONNECTIONG TO SOCKET")
                }
            }
        }
    }

    fun peerObservable() : Flow<PeerRes>
    {
        return peerSocketImp.messageObservable()
    }

     fun isPeerActive() : Boolean
    {
        var isActive = false
        viewModelScope.launch {
            isActive =  peerSocketImp.isSocketActive()
        }
        return isActive
    }


    fun closePeer()
    {
        viewModelScope.launch {
            peerSocketImp.closeSocket()
        }
    }


}
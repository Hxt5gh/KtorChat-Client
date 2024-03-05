package com.hxt5gh.frontend.presentation.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.hxt5gh.frontend.data.remote.userDetail.ChatInfo
import com.hxt5gh.frontend.domain.message.GetMessageRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatScreenViewModel @Inject constructor(private val  getMessageRepositoryImp: GetMessageRepositoryImp) : ViewModel() {

    var chatInfo = mutableStateOf(ChatInfo("" , emptyList())).value




    init {
       userUChatWith(Firebase.auth.uid.toString())
    }



    fun userUChatWith(chatId : String)
    {
        viewModelScope.launch {
            chatInfo = getMessageRepositoryImp.userUChatWith(chatId)
        }
    }

    fun deleteUserChat(userId : String , chatId : String)
    {
        viewModelScope.launch {
          getMessageRepositoryImp.deleteUserChat(userId , chatId)
            delay(200)
            userUChatWith(Firebase.auth.uid.toString())
        }
    }

}
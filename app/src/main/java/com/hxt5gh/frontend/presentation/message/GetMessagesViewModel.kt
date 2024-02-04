package com.hxt5gh.frontend.presentation.message

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hxt5gh.frontend.data.remote.message.Message
import com.hxt5gh.frontend.data.remote.message.MessageApi
import com.hxt5gh.frontend.data.remote.userDetail.UserDataDto
import com.hxt5gh.frontend.domain.message.GetMessageRepositoryImp
import com.hxt5gh.frontend.domain.userDetailRepo.SaveUserRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class GetMessagesViewModel @Inject constructor(
    private val messageRepo : GetMessageRepositoryImp ,
    private val api : MessageApi ,
    private val saveUserRepositoryImp: SaveUserRepositoryImp
) : ViewModel() {



    fun getMessage(chatId: String): List<Message> {
        var result: List<Message>? = null
        runBlocking {
            result = getAllMessagesById(chatId)
        }
        return result ?:emptyList()
    }


     suspend fun getAllMessagesById(chatId : String) : List<Message>
     {

         return messageRepo.getMessageList(chatId)
     }



    fun saveUser(data : UserDataDto) : Boolean{
        var result : Boolean = false
        viewModelScope.launch {
            result = saveUserRepositoryImp.saveUserDetail(data)
        }
        return result
    }

}
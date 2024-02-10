package com.hxt5gh.frontend.domain.message

import com.hxt5gh.frontend.data.remote.message.Message
import com.hxt5gh.frontend.data.remote.message.MessageServicesImp
import com.hxt5gh.frontend.data.remote.userDetail.Response
import com.hxt5gh.frontend.data.remote.userDetail.UserNameSearchServiceImp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMessageRepositoryImp @Inject constructor(private val  messageServices: MessageServicesImp , private val userNameSearchServiceImp: UserNameSearchServiceImp): GetMessageRepository {

    override suspend fun getMessageList(chatId: String): List<Message> {
        return messageServices.getAllMessages(chatId).message
    }

    override suspend fun getSearchedUser(query: String)  : Flow<List<Response>> {

       return  userNameSearchServiceImp.userNameSearchService(query)
    }
}
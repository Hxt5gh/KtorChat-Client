package com.hxt5gh.frontend.presentation.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hxt5gh.frontend.data.remote.message.MessageReceive
import com.hxt5gh.frontend.data.remote.userDetail.Response
import com.hxt5gh.frontend.domain.message.GetMessageRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchScreenViewModel @Inject constructor(private val getMessageRepositoryImp: GetMessageRepositoryImp) : ViewModel(){

    private val _userList = mutableListOf<Response>()
    val userList: List<Response> get() = _userList.toList()

    fun getSearchedUser(query: String) {
        viewModelScope.launch {
            getMessageRepositoryImp.getSearchedUser(query).collect { responseList ->
                responseList.forEach {
                    Log.d("debug", "SearchScreenViewModel: ${it.userId} ${it.userName}")
                }
                if (responseList.isEmpty()){
                    Log.d("debug", "getSearchedUser: ClearList")
                    _userList.clear()
                }else
                {
                    _userList.clear()
                    _userList.addAll(responseList)
                }
            }
        }
    }
}
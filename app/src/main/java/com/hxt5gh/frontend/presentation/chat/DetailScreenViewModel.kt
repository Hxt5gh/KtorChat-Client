package com.hxt5gh.frontend.presentation.chat

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hxt5gh.frontend.domain.userDetailRepo.SaveUserRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailScreenViewModel @Inject constructor() : ViewModel() {

}

sealed class DetailUIEvent{
    object SendMessage : DetailUIEvent()
}
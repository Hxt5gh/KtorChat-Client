package com.hxt5gh.frontend.presentation.chat

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DetailScreenViewModel @Inject constructor() : ViewModel() {



}

sealed class DetailUIEvent{
    object SendMessage : DetailUIEvent()
}
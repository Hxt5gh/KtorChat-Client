package com.hxt5gh.frontend.domain.userDetailRepo

import android.net.Uri
import com.hxt5gh.frontend.data.remote.userDetail.ChatInfo
import com.hxt5gh.frontend.data.remote.userDetail.UserDataDto

interface SaveUserRepository {
    suspend fun saveUserDetail(user : UserDataDto) : Boolean

    suspend fun saveImageToFirebase(uri : Uri) : String

    suspend fun getUseById(userId : String) : UserDataDto
}
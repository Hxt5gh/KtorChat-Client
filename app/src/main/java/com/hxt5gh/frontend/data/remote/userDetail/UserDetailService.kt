package com.hxt5gh.frontend.data.remote.userDetail

import android.net.Uri

interface UserDetailService {
    suspend fun saveUserDetail(user : UserDataDto) : Boolean

    suspend fun saveImageToFirebase(uri : Uri) : String?
}
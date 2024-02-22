package com.hxt5gh.frontend.domain.userDetailRepo

import android.net.Uri
import com.hxt5gh.frontend.data.remote.userDetail.ChatInfo
import com.hxt5gh.frontend.data.remote.userDetail.UserDataDto
import com.hxt5gh.frontend.data.remote.userDetail.UserDetailService
import com.hxt5gh.frontend.data.remote.userDetail.UserDetailServiceImp
import javax.inject.Inject

class SaveUserRepositoryImp @Inject constructor(private val userDetailService: UserDetailServiceImp) : SaveUserRepository {
    override suspend fun saveUserDetail(user: UserDataDto): Boolean {
      return  userDetailService.saveUserDetail(user)
    }

    override suspend fun saveImageToFirebase(uri: Uri) : String {
       return userDetailService.saveImageToFirebase(uri)
    }

    override suspend fun getUseById(userId: String): UserDataDto {
       return userDetailService.getUserById(userId)
    }

}
package com.hxt5gh.frontend.domain.userDetailRepo

import com.hxt5gh.frontend.data.remote.userDetail.UserDataDto
import com.hxt5gh.frontend.data.remote.userDetail.UserDetailService
import com.hxt5gh.frontend.data.remote.userDetail.UserDetailServiceImp
import javax.inject.Inject

class SaveUserRepositoryImp @Inject constructor(private val userDetailService: UserDetailServiceImp) : SaveUserRepository {
    override suspend fun saveUserDetail(user: UserDataDto): Boolean {
      return  userDetailService.saveUserDetail(user)
    }
}
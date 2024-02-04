package com.hxt5gh.frontend.domain.userDetailRepo

import com.hxt5gh.frontend.data.remote.userDetail.UserDataDto

interface SaveUserRepository {
    suspend fun saveUserDetail(user : UserDataDto) : Boolean
}
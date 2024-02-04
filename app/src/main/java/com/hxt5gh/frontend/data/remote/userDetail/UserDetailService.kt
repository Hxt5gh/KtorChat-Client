package com.hxt5gh.frontend.data.remote.userDetail

interface UserDetailService {
    suspend fun saveUserDetail(user : UserDataDto) : Boolean
}
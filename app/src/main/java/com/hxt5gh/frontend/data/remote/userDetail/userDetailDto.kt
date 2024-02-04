package com.hxt5gh.frontend.data.remote.userDetail

import kotlinx.serialization.Serializable


@Serializable
data class UserDataDto(
    val userId : String ,
    val userName : String
)

/*
{
    "userId": "harpreetSingh",
    "userName": "harpreetSingh"
}
*/

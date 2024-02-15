package com.hxt5gh.frontend.data.remote.userDetail

import kotlinx.serialization.Serializable


@Serializable
data class UserDataDto(
    val userId : String ,
    val userName : String,
    val displayName : String,
    val profileUri : String? = null,
)

/*
{
    "userId": "harpreetSingh",
    "userName": "harpreetSingh"
}
*/

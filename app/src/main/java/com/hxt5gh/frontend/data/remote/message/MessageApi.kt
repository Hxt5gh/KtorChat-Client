package com.hxt5gh.frontend.data.remote.message

import retrofit2.http.GET
import retrofit2.http.Query

interface MessageApi {

    //https://api.quotable.io/quotes/?tags=technology&limit=150&sortBy=author

    @GET("getMessages")
    suspend fun getMessages(@Query("id")id : String) : MessageDto
}
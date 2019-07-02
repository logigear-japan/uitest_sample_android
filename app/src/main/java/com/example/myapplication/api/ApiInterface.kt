package com.example.myapplication.api

import com.example.myapplication.api.response.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface ApiConnection {
    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )
    @GET("comments")
    fun commentPostId1(@Query("postId") postId: Int): Single<Array<SinglePostResponse>>

}
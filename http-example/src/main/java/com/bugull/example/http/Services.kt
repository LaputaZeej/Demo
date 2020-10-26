package com.bugull.example.http

import com.bugull.example.http.response.UserResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TestService {
    @FormUrlEncoded
    @POST("/app/login/monitor")
    suspend fun login(
        @Field("userName") userName: String,
        @Field("password") password: String
    ):DefaultResponse<UserResponse>
}
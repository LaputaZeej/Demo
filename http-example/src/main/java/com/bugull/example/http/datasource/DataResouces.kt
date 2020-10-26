package com.bugull.example.http.datasource

import com.bugull.android.http.*
import com.bugull.example.http.*
import com.bugull.example.http.response.UserResponse

interface TestDataResource {
    suspend fun login(userName: String, password: String): Result<UserResponse?>
}

class TestDataResourceImpl : TestDataResource {

    private val testService: TestService by lazy<TestService> {
        createRetrofit(createOkHttpClient(), HOME).createService()
    }

    override suspend fun login(userName: String, password: String): Result<UserResponse?> =
        suspendResult {
             testService.login(userName, password).httpResponse()
        }
}
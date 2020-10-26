package com.bugull.android.ext.repository

import com.bugull.android.ext.data.UserData
import com.bugull.android.ext.data.userData
import com.bugull.android.http.Result
import com.bugull.android.http.convert
import com.bugull.example.http.datasource.TestDataResource
import com.bugull.example.http.datasource.TestDataResourceImpl

class TestRepository {
    private val dataResource: TestDataResource by lazy { TestDataResourceImpl() }

    suspend fun login(username: String, password: String): Result<UserData?> =
        dataResource.login(username,password).convert {
            this.userData()
        }



}
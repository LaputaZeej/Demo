package com.bugull.android.ext.data

import com.bugull.example.http.response.UserResponse

data class UserData(val username: String)

fun UserResponse?.userData() = this?.run {
    UserData(username ?: "")
}
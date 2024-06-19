package com.example.diploma.user_screen.model

import com.google.gson.annotations.SerializedName

data class UserCredResponse(
    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("refresh_token")
    val refreshToken: String
) {
    companion object {
        fun empty() = UserCredResponse(
            accessToken = "",
            refreshToken = ""
        )
    }
}
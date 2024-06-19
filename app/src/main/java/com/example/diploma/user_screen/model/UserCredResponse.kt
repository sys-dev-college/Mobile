package com.example.diploma.user_screen.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserCredResponse(
    @SerializedName("access_token")
    @Expose
    val accessToken: String,

    @SerializedName("refresh_token")
    @Expose
    val refreshToken: String
) {
    companion object {
        fun empty() = UserCredResponse(
            accessToken = "",
            refreshToken = ""
        )
    }
}
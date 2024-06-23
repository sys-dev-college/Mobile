package com.example.diploma.user_screen.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserCredResponse(
    @SerializedName("token")
    @Expose
    val token: Token?,

    @SerializedName("user")
    @Expose
    val user: User?
)

data class Token(
    @SerializedName("access_token")
    @Expose
    val accessToken: String?,

    @SerializedName("refresh_token")
    @Expose
    val refreshToken: String?
)

data class User(
    @SerializedName("id")
    @Expose
    val id: String?,

    @SerializedName("name")
    @Expose
    val name: String?,

    @SerializedName("email")
    @Expose
    val email: String?,

    @SerializedName("telegram_url")
    @Expose
    val telegramUrl: String?,
)
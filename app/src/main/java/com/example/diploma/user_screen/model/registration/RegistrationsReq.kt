package com.example.diploma.user_screen.model.registration

import com.google.gson.annotations.SerializedName

data class RegistrationsReq(
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("first_name")
    val firstName: String,

    @SerializedName("last_name")
    val lastName: String,

    @SerializedName("telegram_url")
    val telegramUrl: String,
)
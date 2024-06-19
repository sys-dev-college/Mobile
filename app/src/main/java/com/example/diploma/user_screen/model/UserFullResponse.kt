package com.example.diploma.user_screen.model

import com.google.gson.annotations.SerializedName

data class UserFullResponse (
    val id: String,
    val name: String,
    val email: String,

    @SerializedName("telegram_url")
    val telegramUrl: String
)


package com.example.diploma.user_screen.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TrainerUser(
    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("email")
    @Expose
    val email: String,

    @SerializedName("telegram_url")
    @Expose
    val telegramUrl: String
)
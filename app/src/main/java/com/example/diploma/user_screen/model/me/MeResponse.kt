package com.example.diploma.user_screen.model.me

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MeResponse(
    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("first_name")
    @Expose
    val firstName: String,

    @SerializedName("last_name")
    @Expose
    val lastName: String,

    @SerializedName("email")
    @Expose
    val email: String,

    @SerializedName("telegram_url")
    @Expose
    val telegramUrl: String
)
package com.example.diploma.user_screen.model

import com.google.gson.annotations.SerializedName

data class UserCred(
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)
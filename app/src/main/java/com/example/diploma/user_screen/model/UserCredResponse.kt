package com.example.diploma.user_screen.model

data class UserCredResponse(
    val detail: List<LoginNet>
)


data class LoginNet(
    val msg: String,
    val type: String
)
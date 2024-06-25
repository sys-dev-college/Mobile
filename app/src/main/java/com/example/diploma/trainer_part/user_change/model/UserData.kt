package com.example.diploma.trainer_part.user_change.model

import java.io.Serializable


class UserData(
    val id: String,
    val name: String,
    val email: String,
    val telegramUrl: String,
) : Serializable
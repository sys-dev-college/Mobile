package com.example.user_interface.models

data class Task(
    val id: Int,
    val cardId: Int,
    val name: String,
    val amount: String,
    val isFinished: Boolean
)

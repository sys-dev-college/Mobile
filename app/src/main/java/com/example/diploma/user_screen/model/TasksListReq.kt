package com.example.diploma.user_screen.model

import com.google.gson.annotations.SerializedName

data class TasksListReq(
    @SerializedName("scheduled")
    val scheduled: String,

    @SerializedName("user_id")
    val userId: String
)
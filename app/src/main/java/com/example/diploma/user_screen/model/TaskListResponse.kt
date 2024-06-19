package com.example.diploma.user_screen.model

import com.google.gson.annotations.SerializedName

data class TaskListResponse(
    @SerializedName("id")
    val id: String,

    @SerializedName("scheduled")
    val scheduled: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("tasks")
    val tasks: List<TaskNet>,

    @SerializedName("assigner")
    val assigner: UserNet,

    @SerializedName("user")
    val user: UserNet,

    @SerializedName("complete")
    val complete: Boolean,
)

data class UserNet(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,
)

data class TaskNet(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("amount")
    val amount: Int,

    @SerializedName("unit")
    val unit: String,

    @SerializedName("completed")
    val completed: Boolean,
)


data class LoginResponse(
    val access_token: String,
    val refresh_token: String
)


//data class UserCredNet(
//    val access_token: String,
//    val refresh_token: String
//)

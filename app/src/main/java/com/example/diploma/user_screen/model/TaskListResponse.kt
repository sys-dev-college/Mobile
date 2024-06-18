package com.example.diploma.user_screen.model

data class TaskResponse(
    val id: String,
    val scheduled: String,
    val title: String,
    val type: Int,
    val tasks: List<TaskNet>,
    val assigner: UserNet,
    val user: UserNet,
    val complete: Boolean,
)

data class UserNet(
    val id: String,
    val user_name: String,
    val email: String,
)

data class TaskNet(
    val id: String,
    val name: String,
    val unit: String,
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

package com.example.diploma.user_screen.retrofit


import com.example.diploma.user_screen.model.TaskListResponse
import com.example.diploma.user_screen.model.TasksListReq
import com.example.diploma.user_screen.model.UserCred
import com.example.diploma.user_screen.model.UserCredResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface Api {
    @POST("/api/calendars/tasks/")
    fun getTasks(@Body req: TasksListReq, @Header("Authorization") user_key: String): Call<TaskListResponse>

    @POST("/api/users/login/")
    fun userAuth(@Body req: UserCred) : Call<UserCredResponse>

}
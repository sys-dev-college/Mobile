package com.example.diploma.user_screen.retrofit


import com.example.diploma.user_screen.model.EmailReq
import com.example.diploma.user_screen.model.TaskListResponse
import com.example.diploma.user_screen.model.TasksListReq
import com.example.diploma.user_screen.model.UserCred
import com.example.diploma.user_screen.model.UserCredResponse
import com.example.diploma.user_screen.model.UserFullResponse
import com.example.diploma.user_screen.model.UserResetResponse
import com.example.diploma.user_screen.model.me.MeResponse
import com.example.diploma.user_screen.model.registration.RegistrationRes
import com.example.diploma.user_screen.model.registration.RegistrationsReq
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {
    @POST("/api/calendars/tasks/")
    fun getTasks(
        @Body req: TasksListReq,
        @Header("Authorization") userToken: String
    ): Call<List<TaskListResponse>>

    @POST("/api/users/login/")
    fun userAuth(@Body req: UserCred): Call<UserCredResponse>

    @POST("/api/users/register/")
    fun userRegister(@Body req: RegistrationsReq): Call<RegistrationRes>

    @POST("api/users/send-restore")
    fun getReset(@Header ("Authorization") userKey: String, @Body req: EmailReq): Call<UserResetResponse>

    @GET("/api/users/{user_id}")
    fun getUserById(
        @Header("Authorization") user_key: String,
        @Path("user_id") userId: String
    ): Call<UserFullResponse>

    @GET("/api/users/me")
    fun getMe(
        @Header("Authorization") userToken: String,
    ): Call<MeResponse>
}
package com.example.diploma.user_screen.retrofit

import com.example.diploma.user_screen.model.CreateCalendarReq
import com.example.diploma.user_screen.model.EmailReq
import com.example.diploma.user_screen.model.TaskListResponse
import com.example.diploma.user_screen.model.TasksListReq
import com.example.diploma.user_screen.model.TrainerUser
import com.example.diploma.user_screen.model.UserCred
import com.example.diploma.user_screen.model.UserCredResponse
import com.example.diploma.user_screen.model.UserFullResponse
import com.example.diploma.user_screen.model.UserResetResponse
import com.example.diploma.user_screen.model.me.MeResponse
import com.example.diploma.user_screen.model.registration.RegistrationRes
import com.example.diploma.user_screen.model.registration.RegistrationsReq
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "http://it-fits.ru"

object RetrofitClient {

    private fun getApi(): Api {
        val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS)
        }.build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(Api::class.java)
    }

    private fun String.makeToken(): String {
        return String.format("Bearer %s", this)
    }

    suspend fun getTasks(
        date: String,
        userToken: String,
        userId: String
    ): List<TaskListResponse> = withContext(Dispatchers.IO) {
        val call: Call<List<TaskListResponse>> =
            getApi().getTasks(TasksListReq(date, userId), userToken.makeToken())
        return@withContext call.execute().body().orEmpty()
    }

    suspend fun loginUser(
        email: String,
        password: String
    ): UserCredResponse? = withContext(Dispatchers.IO) {
        val call: Call<UserCredResponse> = getApi().userAuth(UserCred(email, password))
        return@withContext call.execute().body()
    }

    suspend fun registerUser(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        telegramUrl: String,
    ): RegistrationRes? = withContext(Dispatchers.IO) {
        val call: Call<RegistrationRes> = getApi().userRegister(
            RegistrationsReq(
                email = email,
                password = password,
                firstName = firstName,
                lastName = lastName,
                telegramUrl = telegramUrl
            )
        )
        return@withContext call.execute().body()
    }

    suspend fun getMe(userToken: String): MeResponse? = withContext(Dispatchers.IO) {
        val call: Call<MeResponse> = getApi().getMe(userToken.makeToken())
        return@withContext call.execute().body()
    }

    suspend fun retrieveUser(
        userKey: String,
        userId: String
    ): UserFullResponse? = withContext(Dispatchers.IO) {
        val call: Call<UserFullResponse> = getApi().getUserById(userKey, userId)
        return@withContext call.execute().body()
    }

    suspend fun getUsersList(userToken: String): List<TrainerUser> = withContext(Dispatchers.IO) {
        val call = getApi().getUsers(userToken = userToken.makeToken())
        return@withContext call?.execute()?.body() ?: emptyList()
    }

    suspend fun resetUser(
        userKey: String,
        email: String
    ): UserResetResponse? = withContext(Dispatchers.IO) {
        val call: Call<UserResetResponse> =
            getApi().getReset(userKey.makeToken(), EmailReq(email))
        return@withContext call.execute().body()
    }

    suspend fun setTaskComplete(
        userKey: String,
        taskId: String,
        isComplete: Boolean
    ) {
        withContext(Dispatchers.IO) {
            val call: Call<Unit> = getApi().setTaskComplete(
                userToken = userKey.makeToken(),
                taskId = taskId,
                isCompleted = isComplete
            )
            call.execute()
        }
    }

    suspend fun deleteCalendar(
        userKey: String,
        calendarId: String,
    ) {
        withContext(Dispatchers.IO) {
            val call: Call<Unit> = getApi().deleteCalendar(
                userToken = userKey.makeToken(),
                calendarId = calendarId,
            )
            call.execute()
        }
    }

    fun createCalendar(
        userToken: String,
        clientId: String,
        scheduled: String,
        title: String,
        type: Int
    ) {
        val call: Call<Unit> =
            getApi().createCalendar(
                CreateCalendarReq(clientId, scheduled, title, type),
                userToken.makeToken()
            )
        call.enqueue(object: Callback<Unit>{
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                //do nothing
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                //do nothing
            }
        })

    }
}
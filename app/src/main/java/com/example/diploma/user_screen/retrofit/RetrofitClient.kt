package com.example.diploma.user_screen.retrofit

import android.util.Log
import androidx.lifecycle.MutableLiveData
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

    fun getTasks(
        date: String,
        userToken: String,
        userId: String,
        onResponse: MutableLiveData<List<TaskListResponse>>
    ) {
        val call: Call<List<TaskListResponse>> =
            getApi().getTasks(TasksListReq(date, userId), userToken.makeToken())
        call.enqueue(object : Callback<List<TaskListResponse>> {
            override fun onResponse(
                call: Call<List<TaskListResponse>>,
                response: Response<List<TaskListResponse>>
            ) {
                onResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<List<TaskListResponse>>, t: Throwable) {
                makeLogs(t)
            }
        })
    }

    fun loginUser(
        email: String,
        password: String,
        onResponse: MutableLiveData<UserCredResponse?>
    ) {
        val call: Call<UserCredResponse> = getApi().userAuth(UserCred(email, password))
        call.enqueue(object : Callback<UserCredResponse> {
            override fun onResponse(
                call: Call<UserCredResponse>,
                response: Response<UserCredResponse>
            ) {
                onResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<UserCredResponse>, t: Throwable) {
                makeLogs(t)
            }
        })
    }

    fun registerUser(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        telegramUrl: String,
        onResponse: MutableLiveData<RegistrationRes>
    ) {
        val call: Call<RegistrationRes> = getApi().userRegister(
            RegistrationsReq(
                email = email,
                password = password,
                firstName = firstName,
                lastName = lastName,
                telegramUrl = telegramUrl
            )
        )
        call.enqueue(object : Callback<RegistrationRes> {
            override fun onResponse(
                call: Call<RegistrationRes>,
                response: Response<RegistrationRes>
            ) {
                onResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<RegistrationRes>, t: Throwable) {
                makeLogs(t)
            }
        })
    }

    fun getMe(
        userToken: String,
        onResponse: MutableLiveData<MeResponse?>
    ) {
        val call: Call<MeResponse> = getApi().getMe(userToken.makeToken())
        call.enqueue(object : Callback<MeResponse> {
            override fun onResponse(call: Call<MeResponse>, response: Response<MeResponse>) {
                onResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<MeResponse>, t: Throwable) {
                makeLogs(t)
            }

        })
    }

    fun retrieveUser(
        userKey: String,
        userId: String,
        onResponse: MutableLiveData<UserFullResponse>
    ) {
        val call: Call<UserFullResponse> = getApi().getUserById(userKey, userId)
        call.enqueue(object : Callback<UserFullResponse> {
            override fun onResponse(
                call: Call<UserFullResponse>,
                response: Response<UserFullResponse>
            ) {
                onResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<UserFullResponse>, t: Throwable) {
                makeLogs(t)
            }
        })
    }

    private fun makeLogs(t: Throwable) {
        Log.d("QWE", "onFailure: " + t.message)
    }

    private fun String.makeToken(): String {
        return String.format("Bearer %s", this)
    }

    fun resetUser(
        email: String,
        onResponse: MutableLiveData<UserResetResponse>
    ) {
        val call: Call<UserResetResponse> = getApi().getReset("Bearer", EmailReq(email))
        call.enqueue(object : Callback<UserResetResponse> {
            override fun onResponse(
                call: Call<UserResetResponse>,
                response: Response<UserResetResponse>
            ) {
                onResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<UserResetResponse>, t: Throwable) {
                Log.d("QWE", "onFailure: " + t.message)
            }
        })
    }
}
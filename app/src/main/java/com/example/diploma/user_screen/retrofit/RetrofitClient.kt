package com.example.diploma.user_screen.retrofit

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.diploma.user_screen.model.TaskListResponse
import com.example.diploma.user_screen.model.TasksListReq
import com.example.diploma.user_screen.model.UserCred
import com.example.diploma.user_screen.model.UserCredResponse
import com.example.diploma.user_screen.model.UserFullResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.UUID

object RetrofitClient {

    private var retrofit: Retrofit? = null

    fun getTasks(baseUrl: String, date: String, onResponse: MutableLiveData<TaskListResponse>) {
        val retrofit = getRetrofit(baseUrl)
        val api = retrofit.create(Api::class.java)
        val call: Call<TaskListResponse> = api.getTasks(TasksListReq(date), "Bearer")
        call.enqueue(object : Callback<TaskListResponse> {
            override fun onResponse(
                call: Call<TaskListResponse>,
                response: Response<TaskListResponse>
            ) {
                onResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<TaskListResponse>, t: Throwable) {
                Log.d("QWE", "onFailure: " + t.message)
            }
        })
    }

    fun usersLogin(
        baseUrl: String,
        email: String,
        password: String,
        onResponse: MutableLiveData<UserCredResponse>
    ) {
        val retrofit = getRetrofit(baseUrl)
        val api = retrofit.create(Api::class.java)
        val call: Call<UserCredResponse> = api.userAuth(UserCred(email, password))
        call.enqueue(object : Callback<UserCredResponse> {
            override fun onResponse(
                call: Call<UserCredResponse>,
                response: Response<UserCredResponse>
            ) {
                onResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<UserCredResponse>, t: Throwable) {
                Log.d("QWE", "onFailure: " + t.message)
            }
        })
    }

    fun retrieveUser (
    baseUrl: String,
    userKey: String,
    userId: String,
    onResponse: MutableLiveData<UserFullResponse>
    ) {
        val retrofit = getRetrofit(baseUrl)
        val api = retrofit.create(Api::class.java)
        val call: Call<UserFullResponse> = api.getUserById(userKey, userId)
        call.enqueue(object : Callback<UserFullResponse> {
            override fun onResponse(
                call: Call<UserFullResponse>,
                response: Response<UserFullResponse>
            ) {
                onResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<UserFullResponse>, t: Throwable) {
                Log.d("QWE", "onFailure: " + t.message)
            }
        })
    }

    private fun getRetrofit(baseUrl: String): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}
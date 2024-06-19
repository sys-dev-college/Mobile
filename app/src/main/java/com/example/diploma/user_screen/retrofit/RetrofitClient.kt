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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "http://it-fits.ru"

object RetrofitClient {

    private fun getApi(): Api {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(Api::class.java)
    }

    fun getTasks(date: String, onResponse: MutableLiveData<TaskListResponse>) {
        val call: Call<TaskListResponse> = getApi().getTasks(TasksListReq(date), "Bearer")
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

    fun loginUser(
        email: String,
        password: String,
        onResponse: MutableLiveData<UserCredResponse>
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
        val call: Call<UserFullResponse> = getApi().getUserById(userKey, userId)
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

    fun resetUser(
        email: String,
        onResponse: MutableLiveData<UserResetResponse>
    ){
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
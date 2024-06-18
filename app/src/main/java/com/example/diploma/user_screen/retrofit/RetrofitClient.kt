package com.example.diploma.user_screen.retrofit

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.diploma.user_screen.model.TaskResponse
import com.example.diploma.user_screen.model.TasksListReq
import com.example.diploma.user_screen.model.UserCred
import com.example.diploma.user_screen.model.UserCredResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private var retrofit: Retrofit? = null

    fun getTasks(baseUrl: String, date: String, onResponse: MutableLiveData<List<TaskResponse>>) {
        val retrofit = getRetrofit(baseUrl)
        val api = retrofit.create(Api::class.java)
        val call: Call<List<TaskResponse>> = api.getTasks(TasksListReq(date), "Bearer" )
        call.enqueue(object : Callback<List<TaskResponse>> {
            override fun onResponse(
                call: Call<List<TaskResponse>>,
                response: Response<List<TaskResponse>>
            ) {
                onResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<List<TaskResponse>>, t: Throwable) {
                Log.d("QWE", "onFailure: "+t.message)
            }
        })
    }

    fun usersLogin(baseUrl: String, email: String, password: String, onResponse: MutableLiveData<UserCredResponse>) {
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
                Log.d("QWE", "onFailure: "+t.message)
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
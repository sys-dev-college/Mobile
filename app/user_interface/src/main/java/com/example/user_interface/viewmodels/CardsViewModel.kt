package com.example.user_interface.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.user_interface.models.Card
import com.example.user_interface.models.Task
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException

class CardsViewModel(private val okHttpClient: OkHttpClient) : ViewModel() {
    private val _cards = MutableLiveData<List<Card>>()
    val cards: LiveData<List<Card>> = _cards

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks

    fun getCards(date: String) {
        val request = Request.Builder()
            .url("https://your-backend-url.com/cards?date=$date")
            .build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Handle error
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                val cardsJsonArray = JSONArray(responseBody)
                val cards = ArrayList<Card>()
                for (i in 0 until cardsJsonArray.length()) {
                    val cardJson = cardsJsonArray.getJSONObject(i)
                    cards.add(Card(cardJson.getInt("id"), cardJson.getString("time"), cardJson.getString("name")))
                }
                _cards.postValue(cards)

                // Get tasks for each card
                for (card in cards) {
                    val taskRequest = Request.Builder()
                        .url("https://your-backend-url.com/tasks?cardId=${card.id}")
                        .build()

                    okHttpClient.newCall(taskRequest).enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            // Handle error
                            e.printStackTrace()
                        }

                        override fun onResponse(call: Call, response: Response) {
                            val responseBody = response.body?.string()
                            val tasksJsonArray = JSONArray(responseBody)
                            val tasks = ArrayList<Task>()
                            for (i in 0 until tasksJsonArray.length()) {
                                val taskJson = tasksJsonArray.getJSONObject(i)
                                tasks.add(Task(taskJson.getInt("id"), card.id, taskJson.getString("name"), taskJson.getString("amount"), taskJson.getBoolean("isFinished")))
                            }
                            _tasks.postValue(tasks)
                        }
                    })
                }
            }
        })
    }
}
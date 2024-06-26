package com.example.diploma.user_screen.model.registration

import com.google.gson.annotations.SerializedName

data class CreateTaskReq(
    @SerializedName("calendar_id")
    val calendarId: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("amount")
    val amount: Int,

    @SerializedName("unit")
    val unit: String,
)
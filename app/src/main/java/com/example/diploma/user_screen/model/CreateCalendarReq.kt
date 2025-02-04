package com.example.diploma.user_screen.model

import com.google.gson.annotations.SerializedName

data class CreateCalendarReq (
    @SerializedName("user_id")
    val userId: String,

    @SerializedName("scheduled")
    val scheduled: String,

    @SerializedName("title")
    val title: String,

    /**
     * 0 - питание (не отмечено)
     * 1 - тренировка
     */
    @SerializedName("type")
    val type: Int,
)
package com.example.bomeeapp.data.network.model

data class BookingModel(
    val date: String,
    val description: String,
    val divisions: List<String>,
    val endTime: String,
    val roomId: String,
    val startTime: String
)

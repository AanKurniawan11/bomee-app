package com.example.bomeeapp.data.network.responses.booking.history_all

data class HistoryAllResponse(
    val code: Int,
    val `data`: List<AllHistory>,
    val status: String
)
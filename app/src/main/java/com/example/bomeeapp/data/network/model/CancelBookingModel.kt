package com.example.bomeeapp.data.network.model

data class CancelBookingModel(
    val cancelReason: String,
    val isCanceled: Boolean
)
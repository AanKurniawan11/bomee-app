package com.example.bomeeapp.data.network.responses.booking.cancel

data class Data(
    val cancelReason: String,
    val date: String,
    val description: String,
    val divisions: List<String>,
    val endTime: String,
    val id: String,
    val isApproved: Boolean,
    val isCanceled: Boolean,
    val isRejected: Boolean,
    val rejectReason: Any,
    val room: Room,
    val roomName: String,
    val startTime: String,
    val user: User
)
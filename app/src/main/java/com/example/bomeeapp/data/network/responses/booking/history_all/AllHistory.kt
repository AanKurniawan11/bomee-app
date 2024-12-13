package com.example.bomeeapp.data.network.responses.booking.history_all

data class AllHistory(
    val cancelReason: Any,
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
package com.example.bomeeapp.data.network.responses.booking

import com.example.bomeeapp.data.network.responses.booking.room.RoomData
import com.example.bomeeapp.data.network.responses.profile.UserData

data class BookingData(
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
    val room: RoomData,
    val roomName: String,
    val startTime: String,
    val user: UserData
)

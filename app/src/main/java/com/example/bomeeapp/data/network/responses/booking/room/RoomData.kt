package com.example.bomeeapp.data.network.responses.booking.room

data class RoomData(
    val capacity: Int,
    val facilities: List<String>,
    val id: String,
    val name: String
)
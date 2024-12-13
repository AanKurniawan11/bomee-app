package com.example.bomeeapp.data.network.responses.booking.room

data class RoomResponse (
    val code: Int,
    val `data`: List<RoomData>,
    val paging: Paging,
    val status: String
)
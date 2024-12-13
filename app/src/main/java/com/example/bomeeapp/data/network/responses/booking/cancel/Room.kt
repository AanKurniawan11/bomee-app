package com.example.bomeeapp.data.network.responses.booking.cancel

data class Room(
    val capacity: Int,
    val facilities: List<String>,
    val id: String,
    val name: String
)
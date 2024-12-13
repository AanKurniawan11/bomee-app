package com.example.bomeeapp.data.network.booking

import com.example.bomeeapp.data.local.UserPreferences
import com.example.bomeeapp.data.network.BaseRepository
import com.example.bomeeapp.data.network.model.BookingModel
import com.example.bomeeapp.data.network.model.CancelBookingModel
import com.example.bomeeapp.data.network.model.LoginModel
import javax.inject.Inject

class BookingRepository @Inject constructor(
    private val api: BookingApi,
    private val userPreferences: UserPreferences,
) : BaseRepository() {


    suspend fun getRooms() = safeApiCall({
        api.getRooms()
    }, userPreferences)

    suspend fun getDivisions() = safeApiCall({
        api.getDivisions()
    }, userPreferences)

    suspend fun getHistoryAll() = safeApiCall({
        api.getHistoryAll()
    }, userPreferences)


    suspend fun createBooking(
        date: String,
        description: String,
        divisions: List<String>,
        endTime: String,
        roomId: String,
        startTime: String
    ) = safeApiCall(
        {
            api.createBooking(
                BookingModel(
                    date,
                    description,
                    divisions,
                    endTime,
                    roomId,
                    startTime
                )
            )
        },
        userPreferences
    )

    suspend fun cancelBooking(id: String, cancelReason: String) = safeApiCall(
        {
            api.cancelBooking(id, CancelBookingModel(cancelReason, true))
        },
        userPreferences
    )
}




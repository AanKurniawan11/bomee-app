package com.example.bomeeapp.data.network.booking

import com.example.bomeeapp.data.network.model.BookingModel
import com.example.bomeeapp.data.network.responses.booking.BookingResponse
import com.example.bomeeapp.data.network.responses.booking.cancel.CancelBookingResponse
import com.example.bomeeapp.data.network.responses.booking.division.DivisiResponse
import com.example.bomeeapp.data.network.responses.booking.history_all.HistoryAllResponse
import com.example.bomeeapp.data.network.responses.booking.room.RoomResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import com.example.bomeeapp.data.network.model.CancelBookingModel as CancelBookingModel1

interface BookingApi {

    @GET("rooms")
    suspend fun getRooms(
    ): Response<RoomResponse>

    @GET("divisions")
    suspend fun getDivisions(
    ): Response<DivisiResponse>

    @POST("bookings")
    suspend fun createBooking(
        @Body info: BookingModel
    ): Response<BookingResponse>

    @GET("bookings/current")
    suspend fun getHistoryAll(
    ): Response<HistoryAllResponse>

    @PUT("bookings/cancel/{id}")
    suspend fun cancelBooking(
        @Path("id") id: String,
        @Body info: CancelBookingModel1
    ): Response<CancelBookingResponse>


}
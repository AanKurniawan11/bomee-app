package com.example.bomeeapp.ui.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bomeeapp.data.local.UserPreferences
import com.example.bomeeapp.data.network.Resource
import com.example.bomeeapp.data.network.booking.BookingRepository
import com.example.bomeeapp.data.network.responses.auth.LoginResponse
import com.example.bomeeapp.data.network.responses.booking.BookingResponse
import com.example.bomeeapp.data.network.responses.booking.division.DivisiResponse
import com.example.bomeeapp.data.network.responses.booking.history_all.HistoryAllResponse
import com.example.bomeeapp.data.network.responses.booking.room.RoomResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val bookingRepository: BookingRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private var _rooms: MutableLiveData<Resource<RoomResponse>> = MutableLiveData()
    val rooms: MutableLiveData<Resource<RoomResponse>> get() = _rooms
    fun getRooms() = viewModelScope.launch {
        _rooms.value = Resource.Loading
        _rooms.value = bookingRepository.getRooms()
    }

    private var _divisions: MutableLiveData<Resource<DivisiResponse>> = MutableLiveData()
    val divisions: MutableLiveData<Resource<DivisiResponse>> get() = _divisions
    fun getDivisions() = viewModelScope.launch {
        _divisions.value = Resource.Loading
        _divisions.value = bookingRepository.getDivisions()
    }

    private var _booking: MutableLiveData<Resource<BookingResponse>> = MutableLiveData()
    val booking: LiveData<Resource<BookingResponse>> get() = _booking
    fun createBooking(
        date: String,
        description: String,
        divisions: List<String>,
        endTime: String,
        roomId: String,
        startTime: String
    ) = viewModelScope.launch {
        _booking.value = bookingRepository.createBooking(
            date,
            description,
            divisions,
            endTime,
            roomId,
            startTime
        )
    }


}

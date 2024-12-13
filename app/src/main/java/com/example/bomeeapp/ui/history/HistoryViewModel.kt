package com.example.bomeeapp.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bomeeapp.data.local.UserPreferences
import com.example.bomeeapp.data.network.Resource
import com.example.bomeeapp.data.network.booking.BookingRepository
import com.example.bomeeapp.data.network.responses.booking.cancel.CancelBookingResponse
import com.example.bomeeapp.data.network.responses.booking.history_all.HistoryAllResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyRepository: BookingRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private var _historyAll: MutableLiveData<Resource<HistoryAllResponse>> = MutableLiveData()
    val historyAll: LiveData<Resource<HistoryAllResponse>> get() = _historyAll
    fun getHistoryAll() = viewModelScope.launch {
        _historyAll.value = Resource.Loading
        _historyAll.value = historyRepository.getHistoryAll()
    }

    private var _cancelBooking: MutableLiveData<Resource<CancelBookingResponse>> = MutableLiveData()
    val cancelBooking: LiveData<Resource<CancelBookingResponse>> get() = _cancelBooking
    fun cancelBooking(id: String, cancelReason: String) = viewModelScope.launch {
        _cancelBooking.value = Resource.Loading
        _cancelBooking.value = historyRepository.cancelBooking(id, cancelReason)
    }
}

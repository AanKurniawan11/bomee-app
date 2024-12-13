package com.example.bomeeapp.ui.booking

import android.R
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import com.example.bomeeapp.data.network.Resource
import com.example.bomeeapp.data.network.responses.booking.room.RoomData
import com.example.bomeeapp.databinding.ActivityBookingBinding
import com.example.bomeeapp.ui.result.succes.ResultSuccesActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class BookingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookingBinding
    private val viewModel: BookingViewModel by viewModels()
    private var roomList: List<RoomData> = listOf() // List to hold rooms

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val button = binding.btnBook
        val text = button.text.toString().toLowerCase()
        button.text = text
        viewModel.getRooms()

        viewModel.getDivisions()

        viewModel.booking.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.loadingProgressBar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.loadingProgressBar.visibility = View.GONE
                    val intent = Intent(this, ResultSuccesActivity::class.java)
//                    intent.putExtra("division", resource.value.data.divisions)
                    intent.putExtra("room", resource.value.data.roomName)
                    intent.putExtra("date", resource.value.data.date)
                    intent.putExtra("startTime", resource.value.data.startTime)
                    intent.putExtra("endTime", resource.value.data.endTime)
                    startActivity(intent)
                    finish()
                }

                is Resource.Failure -> {
                    binding.loadingProgressBar.visibility = View.GONE
                    val errorMessage = resource.errorBody?.string() ?: "Unknown error"
                    Log.e("BookingActivity", "Error: $errorMessage")
                }
            }
        }

        binding.btnBook.setOnClickListener {
            val date = binding.editTextDate.text.toString()
            val description = binding.dscBook.text.toString()
            val divisions = listOf(binding.selectDivision.text.toString())
            val startTime = "${date}T${binding.editTextTime.text}:00.000Z"
            val endTime = "${date}T${binding.editTextTime1.text}:00.000Z"
            val roomId = getSelectedRoomId()

            if (date.isNotEmpty() && description.isNotEmpty() && divisions.isNotEmpty() && endTime.isNotEmpty() && roomId != null && roomId.isNotEmpty() && startTime.isNotEmpty()) {
                viewModel.createBooking(date, description, divisions, endTime, roomId, startTime)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }

        }


        viewModel.rooms.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.loadingProgressBar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.loadingProgressBar.visibility = View.GONE

                    roomList = resource.value.data

                    val roomNames = roomList.map { it.name }

                    val adapter = ArrayAdapter(
                        this,
                        android.R.layout.simple_spinner_item,
                        roomNames
                    )

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.spinner1.adapter = adapter
                }

                is Resource.Failure -> {
                    binding.loadingProgressBar.visibility = View.GONE
                    val errorMessage = resource.errorBody?.string() ?: "Unknown error"
                    Log.e("BookingActivity", "Error: $errorMessage")
                }
            }
        }
        viewModel.divisions.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.loadingProgressBar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.loadingProgressBar.visibility = View.GONE
                    val divisions = resource.value.data
                    val divisionNames = divisions.map { it.name }

                    val selectedDivisions = mutableSetOf<String>()

                    binding.selectDivision.setOnClickListener {
                        val builder = AlertDialog.Builder(this)
                            .setTitle("Pilih Divisi")

                        val checkedItems = BooleanArray(divisionNames.size) { false }
                        builder.setMultiChoiceItems(
                            divisionNames.toTypedArray(),
                            checkedItems
                        ) { dialog, which, isChecked ->
                            checkedItems[which] = isChecked
                            selectedDivisions.apply {
                                if (isChecked) {
                                    add(divisionNames[which])
                                } else {
                                    remove(divisionNames[which])
                                }
                            }
                        }

                        builder.setPositiveButton("OK") { dialog, _ ->
                            val selectedText =
                                if (selectedDivisions.isEmpty()) {
                                    "Select Divisions" // Display default text if nothing is selected
                                } else {
                                    selectedDivisions.joinToString(", ") { it } // Combine selected names
                                }
                            binding.selectDivision.text = selectedText
                            dialog.dismiss()
                        }

                        builder.setNegativeButton("Cancel") { dialog, _ ->
                            dialog.dismiss()
                        }

                        builder.create().show()
                    }
                }

                is Resource.Failure -> {
                    binding.loadingProgressBar.visibility = View.GONE
                    val errorMessage = resource.errorBody?.string() ?: "Unknown error"
                    Log.e("BookingActivity", "Error: $errorMessage")
                }
            }
        }
        binding.editTextDate.setOnClickListener {
            showDatePicker()
        }
        binding.editTextTime.setOnClickListener {
            showTimePicker { time -> binding.editTextTime.setText(time) }
        }
        binding.editTextTime1.setOnClickListener {
            showTimePicker { time -> binding.editTextTime1.setText(time) }
        }

    }




    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val date = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
            binding.editTextDate.setText(date)
        }, year, month, day).show()
    }

    private fun getSelectedRoomId(): String? {
        val selectedRoom = binding.spinner1.selectedItem as? String
        return roomList.find { it.name == selectedRoom }?.id
    }


    private fun showTimePicker(onTimeSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            val time = String.format("%02d:%02d", selectedHour, selectedMinute)
            onTimeSelected(time)
        }, hour, minute, true).show()
    }


}


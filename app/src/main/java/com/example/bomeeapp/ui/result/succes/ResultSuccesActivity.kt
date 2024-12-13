package com.example.bomeeapp.ui.result.succes

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bomeeapp.R
import com.example.bomeeapp.databinding.ActivityResultSuccesBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class ResultSuccesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultSuccesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResultSuccesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBook.setOnClickListener {
            finish()
        }
        val room = intent.getStringExtra("room")
        val date = intent.getStringExtra("date")
        val startTime = intent.getStringExtra("startTime")
        val endTime = intent.getStringExtra("endTime")




        binding.tvRoom.text = room
        binding.tvDate.text = formatDate(date)
        binding.tvTime.text = "${formatTime(startTime)} - ${formatTime(endTime)}"
    }

    // Function to reformat time
    private  fun formatTime(timeString: String?): String {
        if (timeString.isNullOrEmpty()) {
            return "" // Handle empty or null time string
        }

        // Handle different date formats
        val formats = arrayOf(
            "yyyy-MM-dd'T'HH:mm:ss",
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        )

        for (format in formats) {
            try {
                val formatter = SimpleDateFormat(format, Locale.US)
                val date = formatter.parse(timeString)
                val outputFormatter = SimpleDateFormat("HH.mm")
                return outputFormatter.format(date)
            } catch (e: ParseException) {
                // Try the next format
            }
        }

        // If none of the formats match, return an empty string or handle the error appropriately
        return ""
    }
    private fun formatDate(dateString: String?): String {
        if (dateString.isNullOrEmpty()) {
            return ""
        }

        val inputFormats = arrayOf(
            "yyyy-MM-dd",
            "yyyy-MM-dd'T'HH:mm:ss",
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" // Tambahkan format lain jika diperlukan
        )
        val outputFormat = "dd MMMM yyyy"

        for (format in inputFormats) {
            try {
                val inputFormatter = SimpleDateFormat(format, Locale.US)
                val date = inputFormatter.parse(dateString)
                val outputFormatter = SimpleDateFormat(outputFormat, Locale.getDefault())
                return outputFormatter.format(date)
            } catch (e: ParseException) {
                // Coba format berikutnya
            }
        }

        return "" // Jika tidak ada format yang cocok
    }
}
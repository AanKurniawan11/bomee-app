package com.example.bomeeapp.ui.cs

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bomeeapp.R
import com.example.bomeeapp.data.network.model.CsModel
import com.example.bomeeapp.databinding.ActivityCsBinding

class CsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCsBinding
    private lateinit var adapter: CsAdapter


    private val csItems = listOf(
        CsModel("Hubungi Kami", "Email: support@example.com\nTelepon: +62 1234 5678 90"),
        CsModel("FAQ", "1. Bagaimana cara menghubungi support?\n2. Apa itu FAQ?"),
        CsModel(
            "Kirim Umpan Balik",
            "Kirimkan umpan balik Anda melalui email ke feedback@example.com."
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewHelp.layoutManager = LinearLayoutManager(this)
        adapter = CsAdapter(csItems)
        binding.recyclerViewHelp.adapter = adapter

        binding.btnBack.setOnClickListener {
            finish()
        }

    }
}
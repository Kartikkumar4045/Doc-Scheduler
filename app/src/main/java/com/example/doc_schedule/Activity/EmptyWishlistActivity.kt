package com.example.doc_schedule.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.example.doc_schedule.databinding.ActivityEmptyWishlistBinding

class EmptyWishlistActivity : BaseActivity() {
    private lateinit var binding: ActivityEmptyWishlistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityEmptyWishlistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }

        binding.favDoctorBtn.setOnClickListener {

            val intent = Intent(this, TopDoctorsActivity::class.java)
            startActivity(intent)
        }
    }
}
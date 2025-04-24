package com.example.doc_schedule.Activity

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.doc_schedule.Domain.DoctorsModel
import com.example.doc_schedule.Manager.WishlistManager
import com.example.doc_schedule.R
import com.example.doc_schedule.databinding.ActivityBookAppointmentBinding


class BookAppointmentActivity : BaseActivity() {

    private lateinit var binding: ActivityBookAppointmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ View binding initialization
        binding = ActivityBookAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get doctor details from Intent
        val name = intent.getStringExtra("name") ?: ""
        val special = intent.getStringExtra("special") ?: ""
        val address = intent.getStringExtra("address") ?: ""
        val patients = intent.getStringExtra("patiens") ?: ""
        val experience = intent.getIntExtra("experience", 0)
        val rating = intent.getDoubleExtra("rating", 0.0)
        val image = intent.getStringExtra("image") ?: ""

        val doctor = DoctorsModel(
            Name = name,
            Special = special,
            Address = address,
            Patiens = patients,
            Expriense = experience,
            Rating = rating,
            Picture = image,
            Biography = "",
            Location = "",
            Mobile = "",
            Site = "",
            Id = 0
        )

        // UI References
        val nameTxt = findViewById<TextView>(R.id.titleTxt)
        val specialTxt = findViewById<TextView>(R.id.specialTxt)
        val ratingTxt = findViewById<TextView>(R.id.ratingTxt)
        val addressTxt = findViewById<TextView>(R.id.addressTxt)
        val experienceTxt = findViewById<TextView>(R.id.experienceTxt)
        val patientTxt = findViewById<TextView>(R.id.patiensTxt)
        val imageView = findViewById<ImageView>(R.id.img)
        val favBtn = findViewById<ImageView>(R.id.favBtn)
        val backBtn = findViewById<ImageView>(R.id.backBtn)

        // Set doctor details
        nameTxt.text = name
        specialTxt.text = special
        ratingTxt.text = "$rating"
        addressTxt.text = address
        experienceTxt.text = "$experience year"
        patientTxt.text = patients
        Glide.with(this).load(image).into(imageView)

        // Wishlist icon
        fun updateFavIcon(isFav: Boolean) {
            favBtn.setImageResource(if (isFav) R.drawable.favorite_white else R.drawable.favorite_white)
        }

        var isFavorite = WishlistManager.contains(doctor)
        updateFavIcon(isFavorite)

        favBtn.setOnClickListener {
            isFavorite = if (isFavorite) {
                WishlistManager.remove(doctor)
                Toast.makeText(this, "Removed from wishlist", Toast.LENGTH_SHORT).show()
                false
            } else {
                WishlistManager.add(doctor)
                Toast.makeText(this, "Added to wishlist", Toast.LENGTH_SHORT).show()
                true
            }
            updateFavIcon(isFavorite)
        }

        backBtn.setOnClickListener {
            onBackPressed()
        }

        // Date Picker
        binding.etDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(this, { _, y, m, d ->
                binding.etDate.setText(String.format("%02d/%02d/%04d", d, m + 1, y))
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        // Time Picker
        binding.etTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            TimePickerDialog(this, { _, h, m ->
                val amPm = if (h < 12) "AM" else "PM"
                val hour = if (h % 12 == 0) 12 else h % 12
                binding.etTime.setText(String.format("%02d:%02d %s", hour, m, amPm))
            }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false).show() // 'false' indicates 12-hour format
        }

        // Submit Button
        binding.btnSubmitAppointment.setOnClickListener {
            val userName = binding.etName.text.toString().trim()
            val userPhone = binding.etPhone.text.toString().trim()
            val userDate = binding.etDate.text.toString().trim()
            val userTime = binding.etTime.text.toString().trim()
            val reason = binding.etReason.text.toString().trim()

            if (userName.isEmpty() || userPhone.isEmpty() || userDate.isEmpty() || userTime.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
            } else {
                // Toast
                Toast.makeText(this, "Appointment booked for $userName on $userDate at $userTime", Toast.LENGTH_LONG).show()

                // SMS Content
                val doctorName = intent.getStringExtra("name") ?: "Doctor"
                val special = intent.getStringExtra("special") ?: "Specialist"
                val patients = intent.getStringExtra("patiens") ?: "N/A"

                val message = """
            Hi $userName,
            Your appointment with $doctorName ($special) is confirmed on $userDate at $userTime.
            Doctor has treated $patients.
            Reason: $reason
        """.trimIndent()

                // Check permission and send SMS
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), 1001)
                } else {
                    sendSMS(userPhone, message)
                }
            }
        }


    }

    private fun sendSMS(phoneNumber: String, message: String) {

        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Toast.makeText(this, "SMS sent successfully", Toast.LENGTH_SHORT).show()
        }

        catch (e: Exception) {
            Toast.makeText(this, "Failed to send SMS: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            if (requestCode == 1001) {
                    if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            // Permission granted, you can send the SMS here if needed
                            val userPhone = binding.etPhone.text.toString().trim()
                            val message = "Your message here" // Replace with your actual message
                            sendSMS(userPhone, message)
                        } else {
                            Toast.makeText(this, "SMS permission denied", Toast.LENGTH_SHORT).show()
                        }
                }
        }

}

package com.example.doc_schedule.Activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.doc_schedule.R
import com.example.doc_schedule.databinding.ActivityBookAppointmentBinding

class BookAppointmentActivity : BaseActivity() {

    private lateinit var titleTxt: TextView
    private lateinit var specialTxt: TextView
    private lateinit var addressTxt: TextView
    private lateinit var patientsTxt: TextView
    private lateinit var experienceTxt: TextView
    private lateinit var ratingTxt: TextView
    private lateinit var img: ImageView
    private lateinit var backBtn: ImageView

    private lateinit var binding: ActivityBookAppointmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize views
        titleTxt = findViewById(R.id.titleTxt)
        specialTxt = findViewById(R.id.specialTxt)
        addressTxt = findViewById(R.id.addressTxt)
        patientsTxt = findViewById(R.id.patiensTxt)
        experienceTxt = findViewById(R.id.experienceTxt)
        ratingTxt = findViewById(R.id.ratingTxt)
        img = findViewById(R.id.img)
        backBtn = findViewById(R.id.backBtn)

        // Get data from intent
        val name = intent.getStringExtra("name")
        val special = intent.getStringExtra("special")
        val address = intent.getStringExtra("address")
        val patients = intent.getStringExtra("patiens")
        val experience = intent.getIntExtra("experience", 0)
        val rating = intent.getDoubleExtra("rating", 0.0)
        val image = intent.getStringExtra("image")

        // Set data to views
        titleTxt.text = name
        specialTxt.text = special
        addressTxt.text = address
        patientsTxt.text = patients
        experienceTxt.text = "$experience yrs"
        ratingTxt.text = rating.toString()

        Glide.with(this).load(image).into(img)

        backBtn.setOnClickListener {
            onBackPressed()
        }

        // Date Picker
        binding.etDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, y, m, d ->
                binding.etDate.setText(String.format("%02d/%02d/%04d", d, m + 1, y))
            }, year, month, day)

            datePickerDialog.show()
        }

        // Time Picker
        binding.etTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(this, { _, h, m ->
                binding.etTime.setText(String.format("%02d:%02d", h, m))
            }, hour, minute, true)

            timePickerDialog.show()
        }

        // Confirm Button
        binding.btnSubmitAppointment.setOnClickListener {
            val userName = binding.etName.text.toString().trim()
            val userPhone = binding.etPhone.text.toString().trim()
            val userDate = binding.etDate.text.toString().trim()
            val userTime = binding.etTime.text.toString().trim()
            val reason = binding.etReason.text.toString().trim()

            if (userName.isEmpty() || userPhone.isEmpty() || userDate.isEmpty() || userTime.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Appointment booked for $userName on $userDate at $userTime", Toast.LENGTH_LONG).show()
            }
        }
    }

}

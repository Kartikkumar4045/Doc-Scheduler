package com.example.doc_schedule.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.doc_schedule.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("DocSchedulePrefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
            navigateToIntro()
            return
        }

        binding.BtnLogin.setOnClickListener {
            val email = binding.txtEmail.text.toString().trim()
            val password = binding.txtPassword.text.toString().trim()

            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()

            when {
                email.isEmpty() || password.isEmpty() -> {
                    Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
                }
                !email.matches(emailPattern) -> {
                    Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    sharedPref.edit().apply {
                        putString("email", email)
                        putString("password", password)
                        putBoolean("isLoggedIn", true)
                        apply()
                    }
                    navigateToIntro()
                }
            }
        }

    }

    private fun navigateToIntro() {
        startActivity(Intent(this, IntroActivity::class.java))
        finish()
    }
}

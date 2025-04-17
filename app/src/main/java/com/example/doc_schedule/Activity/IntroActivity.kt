package com.example.doc_schedule.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.doc_schedule.R

class IntroActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val startBtn = findViewById<Button>(R.id.startBtn)

        startBtn.setOnClickListener {
            startActivity(Intent(this@IntroActivity, MainActivity::class.java))
        }
    }
}

package com.example.doc_schedule.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.RecyclerView
import com.example.doc_schedule.Adapter.WishlistAdapter
import com.example.doc_schedule.Manager.WishlistManager
import com.example.doc_schedule.databinding.ActivityWishlistBinding

class WishlistActivity : BaseActivity() {

    private lateinit var binding: ActivityWishlistBinding // Declare the binding variable
    private lateinit var recyclerView: RecyclerView // Declare the RecyclerView variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityWishlistBinding.inflate(layoutInflater) // Inflate the binding
        setContentView(binding.root) // Set the content view to the root of the binding

        // Initialize the RecyclerView
        recyclerView = binding.viewWishlistList // Use binding to access the RecyclerView

        binding.backBtn.setOnClickListener { finish() } // Use binding to access backBtn
    }

    override fun onResume() {
        super.onResume()
        val list = WishlistManager.fetchWishlist()

        if (list.isEmpty()) {
            val intent = Intent(this, EmptyWishlistActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val adapter = WishlistAdapter(list.toMutableList())
            recyclerView.adapter = adapter
        }
    }
}
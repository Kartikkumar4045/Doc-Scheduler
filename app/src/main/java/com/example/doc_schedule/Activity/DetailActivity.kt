package com.example.doc_schedule.Activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.doc_schedule.Domain.DoctorsModel
import com.example.doc_schedule.Manager.WishlistManager
import com.example.doc_schedule.R
import com.example.doc_schedule.databinding.ActivityDetailBinding

class DetailActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var item: DoctorsModel
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDoctorDetails()
    }

    private fun getDoctorDetails() {
        item = intent.getParcelableExtra("object")!!

        binding.apply {
            titleTxt.text = item.Name
            specialTxt.text = item.Special
            patiensTxt.text = item.Patiens
            bioTxt.text = item.Biography
            addressTxt.text = item.Address
            experienceTxt.text = "${item.Expriense} year"
            ratingTxt.text = "${item.Rating}"
            Glide.with(this@DetailActivity).load(item.Picture).into(img)

            backBtn.setOnClickListener { finish() }

            websiteBtn.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(item.Site))
                startActivity(i)
            }

            messageBtn.setOnClickListener {
                val uri = Uri.parse("smsto:${item.Mobile}")
                val intent = Intent(Intent.ACTION_SENDTO, uri)
                intent.putExtra("sms_body", "the SMS text")
                startActivity(intent)
            }

            callBtn.setOnClickListener {
                val uri = "tel:${item.Mobile.trim()}"
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse(uri))
                startActivity(intent)
            }

            directionBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.Location))
                startActivity(intent)
            }

            shareBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_SUBJECT, item.Name)
                intent.putExtra(Intent.EXTRA_TEXT, "${item.Name} ${item.Address} ${item.Mobile}")
                startActivity(Intent.createChooser(intent, "Choose one"))
            }

            makeBtn.setOnClickListener {
                val intent = Intent(this@DetailActivity, BookAppointmentActivity::class.java)
                intent.putExtra("name", item.Name)
                intent.putExtra("special", item.Special)
                intent.putExtra("address", item.Address)
                intent.putExtra("patiens", item.Patiens)
                intent.putExtra("experience", item.Expriense)
                intent.putExtra("rating", item.Rating)
                intent.putExtra("image", item.Picture)
                startActivity(intent)
            }

            // Wishlist functionality
            isFavorite = WishlistManager.contains(item)
            updateFavIcon(isFavorite)

            favBtn.setOnClickListener {
                isFavorite = if (isFavorite) {
                    WishlistManager.remove(item)
                    Toast.makeText(this@DetailActivity, "Removed from wishlist", Toast.LENGTH_SHORT).show()
                    false
                } else {
                    WishlistManager.add(item)
                    Toast.makeText(this@DetailActivity, "Added to wishlist", Toast.LENGTH_SHORT).show()
                    true
                }
                updateFavIcon(isFavorite)
            }
        }
    }

    private fun updateFavIcon(isFav: Boolean) {
        binding.favBtn.setImageResource(
            if (isFav) R.drawable.favorite_white else R.drawable.favorite_white
        )
    }
}

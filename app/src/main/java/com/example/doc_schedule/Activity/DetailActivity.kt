package com.example.doc_schedule.Activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.doc_schedule.Domain.DoctorsModel
import com.example.doc_schedule.databinding.ActivityDetailBinding

class DetailActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var item: DoctorsModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getBundle()
    }

    private fun getBundle() {
        item = intent.getParcelableExtra("object")!!

        binding.apply {
            titleTxt.text = item.Name
            specialTxt.text = item.Special
            patiensTxt.text = item.Patiens
            bioTxt.text = item.Biography
            addressTxt.text = item.Address
            experienceTxt.text = "${item.Expriense} year"
            ratingTxt.text = "${item.Rating}"

            backBtn.setOnClickListener { finish() }

            websiteBtn.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(item.Site)
                startActivity(i)
            }

            messageBtn.setOnClickListener {
                val uri = Uri.parse("smsto:${item.Mobile}")
                val intent = Intent(Intent.ACTION_SENDTO, uri)
                intent.putExtra("sms_body", "the SMS text")
                startActivity(intent)
            }

            callBtn.setOnClickListener {
                val uri = "tel:" + item.Mobile.trim()
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

            Glide.with(this@DetailActivity)
                .load(item.Picture)
                .into(img)

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
        }
    }
}

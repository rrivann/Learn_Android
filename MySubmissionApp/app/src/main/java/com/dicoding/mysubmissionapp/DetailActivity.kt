package com.dicoding.mysubmissionapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.dicoding.mysubmissionapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_CRYPTO = "extra_crypto"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val crypto = if (Build.VERSION.SDK_INT >= 33)
            intent.getParcelableExtra(EXTRA_CRYPTO, Crypto::class.java) else
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_CRYPTO)

        Glide.with(this).load(crypto?.photo).into(binding.imgDetailPhoto)
        binding.tvDetailName.text = crypto?.name
        binding.tvDetailDesc.text = crypto?.description
        binding.actionShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, crypto?.description)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)

        }

    }
}
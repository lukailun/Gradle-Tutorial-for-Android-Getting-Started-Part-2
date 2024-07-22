package com.kodeco.socializify

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kodeco.socializify.databinding.ActivityProfileBinding
import com.squareup.picasso.Picasso

class ProfileActivity : AppCompatActivity() {

  private lateinit var binding: ActivityProfileBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityProfileBinding.inflate(layoutInflater)
    setContentView(binding.root)

    loadAvatar()
  }

  private fun loadAvatar() {
    Picasso.get().load("https://goo.gl/tWQB1a").into(binding.avatar)
  }
}

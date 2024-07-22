package com.kodeco.socializify

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kodeco.socializify.databinding.ActivityProfileBinding
import com.squareup.picasso.Picasso

class ProfileActivity : AppCompatActivity() {

  companion object {
    const val PAID_FLAVOR = "paid"
  }

  private lateinit var binding: ActivityProfileBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityProfileBinding.inflate(layoutInflater)
    setContentView(binding.root)
    loadAvatar()
    showMessage()
    togglePhotosVisibility()
  }

  private fun loadAvatar() {
    Picasso.get().load("https://goo.gl/tWQB1a").into(binding.avatar)
  }

  private fun isAppPaid() = BuildConfig.FLAVOR == PAID_FLAVOR

  private fun showMessage() {
    val message = if (isAppPaid()) R.string.paid_app_message else R.string.free_app_message
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
  }

  private fun togglePhotosVisibility() {
    binding.extraPhotos.visibility = if (isAppPaid()) View.VISIBLE else View.GONE
    binding.restriction.visibility = if (isAppPaid()) View.GONE else View.VISIBLE
  }
}
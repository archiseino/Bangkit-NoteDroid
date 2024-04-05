package com.example.project_submission_android_bangkit

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.project_submission_android_bangkit.adapter.Driver
import com.example.project_submission_android_bangkit.databinding.ActivityDetailBinding

class ActivityDetail : AppCompatActivity() {

    companion object {
        const val EXTRA_DRIVER = "extra_driver"
    }

    private var _binding : ActivityDetailBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val driver = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_DRIVER, Driver::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_DRIVER)
        }

        if (driver != null) {
            binding.ivDriver.setImageResource(driver.driverImage)
            binding.tvFrameDriverName.text = driver.driverName
            binding.tvDriverName.text = driver.driverName
            binding.tvDriverTeam.text = driver.driverTeam
            binding.tvTablesDriverTeam.text = driver.driverTeam
            binding.tvTablesDriverCountry.text = driver.driverNationality
            binding.tvTablesDriverPodium.text = driver.driverPodium
            binding.tvTablesDriverDoB.text = driver.driverDoB
            binding.tvTablesDriverPoB.text = driver.driverPoB
            binding.tvDriverDetailDesc.text = driver.driverLongDesc
        }

        binding.actionShare.setOnClickListener{
            val driverSummary = getString(
                R.string.driver,
                driver?.driverName,
                driver?.driverNationality,
                driver?.driverTeam,
                driver?.driverPodium
            )
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, driverSummary)
                type = "text/plain"
            }

            startActivity(sendIntent)
        }

    }



}
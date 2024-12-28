package com.example.project_submission_android_bangkit

import MarginItemDecoration
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.project_submission_android_bangkit.adapter.Driver
import com.example.project_submission_android_bangkit.adapter.DriverAdapter
import com.example.project_submission_android_bangkit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var listDriver = ArrayList<Driver>()
    private lateinit var driverAdapter : DriverAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getAllDriver()

        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        driverAdapter = DriverAdapter(listDriver) {driver ->
            val moveToDetail = Intent(this@MainActivity, ActivityDetail::class.java)
            moveToDetail.putExtra(ActivityDetail.EXTRA_DRIVER , driver)
            startActivity(moveToDetail)
        }
        binding.rvDrivers.adapter = driverAdapter
        binding.rvDrivers.addItemDecoration(MarginItemDecoration(24))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profile_menu -> {
                val moveToProfile = Intent(this@MainActivity, ActivityProfile::class.java)
                startActivity(moveToProfile)
            }

        }
        return super.onOptionsItemSelected(item)

    }


    private fun getAllDriver(): Collection<Driver> {
        val driverImage =  resources.obtainTypedArray(R.array.driver_image)
        val driverNationalityFlag = resources.obtainTypedArray(R.array.driver_nationality_flag)
        val driverNationality = resources.getStringArray(R.array.driver_nationality)
        val driverName = resources.getStringArray(R.array.driver_name)
        val driverNumber = resources.getStringArray(R.array.driver_number)
        val driverTeam = resources.getStringArray(R.array.driver_team)
        val driverPodium = resources.getStringArray(R.array.driver_podium)
        val driverDoB = resources.getStringArray(R.array.driver_dob)
        val driverPoB = resources.getStringArray(R.array.driver_pob)
        val driverShortDesc = resources.getStringArray(R.array.driver_rv_desc)
        val driverLongDesc = resources.getStringArray(R.array.driver_desc)

        for (i in driverName.indices) {
            val driver = Driver(
                driverImage.getResourceId(i, -1), driverNationalityFlag.getResourceId(i, -1), driverNationality[i],
                driverName[i], driverNumber[i], driverTeam[i], driverPodium[i],
                driverDoB[i], driverPoB[i], driverShortDesc[i], driverLongDesc[i])
            listDriver.add(driver)
        }

        return listDriver
    }



}


package com.example.project_submission_android_bangkit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project_submission_android_bangkit.databinding.ItemDriverBinding

class DriverAdapter(private var listDriver : ArrayList<Driver>, private val onDriverClicked: (Driver) -> Unit) : RecyclerView.Adapter<DriverAdapter.DriverAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverAdapterViewHolder {
        val binding = ItemDriverBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DriverAdapterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listDriver.size
    }

    override fun onBindViewHolder(holder: DriverAdapterViewHolder, position: Int) {
        val currentDriver = listDriver[position]
        holder.itemView.setOnClickListener{
            onDriverClicked(currentDriver)
        }
        holder.bind(currentDriver)
    }

    class DriverAdapterViewHolder(private val binding : ItemDriverBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(driver : Driver) {
            Glide.with(binding.root.context).load(driver.driverNationalityFlag).into(binding.ivFlag)
            binding.tvTitleName.text = driver.driverName
            binding.tvTeamName.text = driver.driverTeam
            Glide.with(binding.root.context).load(driver.driverImage).into(binding.ivDriver)
            binding.tvDriverNumber.text = driver.driverNumber
            binding.tvContentDriverName.text = driver.driverName
            binding.tvDriverDesc.text = driver.driverShortDesc
        }
    }

}
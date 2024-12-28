package com.example.projectsubmissionandroidfundamentalbangkit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.projectsubmissionandroidfundamentalbangkit.data.response.UserResponse
import com.example.projectsubmissionandroidfundamentalbangkit.databinding.ItemProfileBinding

class UserResponseAdapter: ListAdapter<UserResponse, UserResponseAdapter.UserResponseViewHolder>(DIFF_CALLBACK) {
    private var onItemClickCallback : OnItemClickCallback? = null

    interface OnItemClickCallback {
        fun onItemClicked(model : UserResponse)
    }

    fun setOnItemCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserResponseViewHolder {
        val  binding = ItemProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserResponseViewHolder(binding)
    }

    class UserResponseViewHolder(private val binding : ItemProfileBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemsItem : UserResponse) {
            Glide.with(binding.root.context).load(itemsItem.avatarUrl).into(binding.ivProfilePic)
            binding.tvProfileUsername.text = itemsItem.login
        }
    }

    override fun onBindViewHolder(holder: UserResponseViewHolder, position: Int) {
        getItem(position).let { user ->
            holder.itemView.setOnClickListener{
                if (onItemClickCallback != null) {
                    onItemClickCallback!!.onItemClicked(user)
                }
            }
            holder.bind(user)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserResponse>() {
            override fun areItemsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
                return oldItem == newItem
            }
        }
    }


}


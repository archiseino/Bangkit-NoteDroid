package com.example.projectsubmissionandroidfundamentalbangkit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectsubmissionandroidfundamentalbangkit.data.local.entity.UserEntity
import com.example.projectsubmissionandroidfundamentalbangkit.databinding.ItemProfileBinding

class UserEntityAdapter : ListAdapter<UserEntity, UserEntityAdapter.UserEntityViewHolder>(DIFF_CALLBACK) {
    private var onItemClickCallback : OnItemClickCallback? = null

    interface OnItemClickCallback {
        fun onItemClicked(model: UserEntity)
    }

    fun setOnItemCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }



    class UserEntityViewHolder(private val binding: ItemProfileBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(itemsItem : UserEntity) {
            Glide.with(binding.root.context).load(itemsItem.avatarUrl).into(binding.ivProfilePic)
            binding.tvProfileUsername.text = itemsItem.login
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserEntityViewHolder {
        val binding = ItemProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserEntityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserEntityViewHolder, position: Int) {
        getItem(position).let {user ->
            holder.itemView.setOnClickListener{
                if (onItemClickCallback != null) {
                    onItemClickCallback!!.onItemClicked(user)
                }
            }
            holder.bind(user)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserEntity>() {
            override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                return oldItem.login == newItem.login
            }

            override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

}
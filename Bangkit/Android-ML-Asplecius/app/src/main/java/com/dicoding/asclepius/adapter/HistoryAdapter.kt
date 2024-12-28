package com.dicoding.asclepius.adapter

import android.net.Uri
import android.service.media.MediaBrowserService
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dicoding.asclepius.data.local.room.ResultHistory
import com.dicoding.asclepius.data.remote.response.ArticlesItem
import com.dicoding.asclepius.databinding.ItemListBinding

class HistoryAdapter : ListAdapter<ResultHistory, HistoryAdapter.HistoryAdapterViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryAdapter.HistoryAdapterViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryAdapter.HistoryAdapterViewHolder, position: Int) {
        getItem(position).let {
            holder.bind(it)
        }
    }

    class HistoryAdapterViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(resultHistory: ResultHistory) {
            with (binding) {
                ivImage.load(Uri.parse(resultHistory.imageUri))
                tvTitle.text = resultHistory.label
                tvDescription.text = resultHistory.score
            }
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ResultHistory>() {
            override fun areItemsTheSame(oldItem: ResultHistory, newItem: ResultHistory): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ResultHistory, newItem: ResultHistory): Boolean {
                return oldItem == newItem
            }
        }
    }

}
package com.example.storyapp.ui.home

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.amulyakhare.textdrawable.TextDrawable
import com.example.storyapp.data.stories.ListStoryItem
import com.example.storyapp.databinding.ItemStoryBinding
import com.example.storyapp.utils.Colors
import com.example.storyapp.utils.getTextDrawable

class HomeAdapter : PagingDataAdapter<ListStoryItem, HomeAdapter.HomeAdapterViewHolder>(DIFF_CALLBACK) {

    private var onItemClickCallback : OnItemClickCallback? = null

    interface OnItemClickCallback {
        fun onItemClicked(model: ListStoryItem)
    }

    fun setOnItemCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class HomeAdapterViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: ListStoryItem) {
            with(binding) {
                ivStory.load(story.photoUrl)
                tvTitle.text = story.name
                tvDescription.text = story.description
                val textDrawable = getTextDrawable(Colors.getRandomColor(), TextDrawable.SHAPE_ROUND, story.name?.first().toString())
                ivAvatar.setImageDrawable(textDrawable)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeAdapterViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeAdapterViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.itemView.setOnClickListener {
                if (onItemClickCallback != null) {
                    onItemClickCallback!!.onItemClicked(item)
                }
            }
            holder.bind(item)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}
package com.example.flickscout.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.flickscout.core.databinding.ItemListMoviesBinding
import com.example.flickscout.core.domain.model.Movie

class MoviesAdapter(private val onItemClicked: (Movie) -> Unit): ListAdapter<Movie, MoviesAdapter.MoviesViewHolder>(DIFF_CALLBACK) {

    inner class MoviesViewHolder(private val binding : ItemListMoviesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(movie: Movie) {
            binding.ivItemImage.load("https://image.tmdb.org/t/p/w500"+ movie.posterPath)
            binding.tvItemTitle.text = movie.title
            binding.tvItemSubtitle.text = movie.overview
        }
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { movie ->
            holder.itemView.setOnClickListener {
                onItemClicked(movie)
            }
            holder.bind(movie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding = ItemListMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(binding)
    }

    companion object  {
        val DIFF_CALLBACK : DiffUtil.ItemCallback<Movie> =
            object : DiffUtil.ItemCallback<Movie>() {
                override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                    return oldItem == newItem
                }

                override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                    return oldItem.id == newItem.id
                }

            }
    }

}
package com.example.shrine.staggeredgridlayout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.shrine.data.Goods
import com.example.shrine.data.ProductEntry
import com.example.shrine.databinding.ShrStaggeredProductCardFirstBinding
import com.example.shrine.databinding.ShrStaggeredProductCardSecondBinding
import com.example.shrine.databinding.ShrStaggeredProductCardThirdBinding

/**
 * Adapter used to show an asymmetric grid of products, with 2 items in the first column, and 1
 * item in the second column, and so on.
 */
class StaggeredProductCardRecyclerViewAdapter(private val productList: ArrayList<Goods>) : RecyclerView.Adapter<StaggeredProductCardRecyclerViewAdapter.StaggeredProductCardViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return position % 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaggeredProductCardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = when (viewType) {
            0 -> ShrStaggeredProductCardFirstBinding.inflate(inflater, parent, false)
            1 -> ShrStaggeredProductCardSecondBinding.inflate(inflater, parent, false)
            2 -> ShrStaggeredProductCardThirdBinding.inflate(inflater, parent, false)
            else -> throw IllegalArgumentException("Invalid view type")
        }
        return StaggeredProductCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StaggeredProductCardViewHolder, position: Int) {
        val goods = productList[position]
        val productEntry = when (position % 3) {
            0 -> ProductEntry.FirstType(goods.goodsName, goods.goodsPrice, goods.goodsImage)
            1 -> ProductEntry.SecondType(goods.goodsName, goods.goodsPrice, goods.goodsImage)
            2 -> ProductEntry.ThirdType(goods.goodsName, goods.goodsPrice, goods.goodsImage)
            else -> throw IllegalArgumentException("Invalid position")
        }
        holder.bind(productEntry)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class StaggeredProductCardViewHolder(private var binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductEntry) {
            when (product) {
                is ProductEntry.FirstType -> bindFirstType(product)
                is ProductEntry.SecondType -> bindSecondType(product)
                is ProductEntry.ThirdType -> bindThirdType(product)
            }
        }

        private fun bindFirstType(product: ProductEntry.FirstType) {
            val binding = binding as ShrStaggeredProductCardFirstBinding
            binding.productTitle.text = product.title
            binding.productPrice.text = product.price
            Glide.with(binding.root.context).load(product.imageUrl).into(binding.productImage)
        }

        private fun bindSecondType(product: ProductEntry.SecondType) {
            val binding = binding as ShrStaggeredProductCardSecondBinding
            binding.productTitle.text = product.title
            binding.productPrice.text = product.price
            Glide.with(binding.root.context).load(product.imageUrl).into(binding.productImage)
        }

        private fun bindThirdType(product: ProductEntry.ThirdType) {
            val binding = binding as ShrStaggeredProductCardThirdBinding
            binding.productTitle.text = product.title
            binding.productPrice.text = product.price
            Glide.with(binding.root.context).load(product.imageUrl).into(binding.productImage)
        }

    }

}

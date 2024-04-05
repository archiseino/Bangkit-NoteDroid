package com.example.shrine.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shrine.NavigationIconClickListener
import com.example.shrine.staggeredgridlayout.ProductGridItemDecoration
import com.example.shrine.R
import com.example.shrine.data.Goods
import com.example.shrine.databinding.ShrProductGridFragmentBinding
import com.example.shrine.staggeredgridlayout.StaggeredProductCardRecyclerViewAdapter
import com.google.android.material.appbar.MaterialToolbar

class ProductGridFragment : Fragment() {

    private lateinit var binding : ShrProductGridFragmentBinding
    private lateinit var appBar : MaterialToolbar
    private lateinit var productGrid : NestedScrollView
    private lateinit var recyclerView : RecyclerView
    private var listGoods =  ArrayList<Goods>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment with the ProductGrid theme
        binding = ShrProductGridFragmentBinding.inflate(inflater, container, false)

        getGoods()
        setupToolBar()
        setRecyclerView()

        return binding.root
    }

    private fun setupToolBar() {
        appBar =  binding.appBar
        productGrid = binding.productGrid
        appBar.setNavigationOnClickListener(
            NavigationIconClickListener(
                requireActivity(),
                productGrid,
                AccelerateDecelerateInterpolator(),
                ContextCompat.getDrawable(requireContext(), R.drawable.shr_branded_menu), // Menu open icon
                ContextCompat.getDrawable(requireContext(), R.drawable.shr_close_menu)) // Menu close icon
        )

        // Set cut corner background for API 23+
        productGrid.background = AppCompatResources.getDrawable(requireContext(),
            R.drawable.shr_product_grid_background_shape
        )
    }

    private fun setRecyclerView() {
        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        val gridLayoutManager = GridLayoutManager(context, 2, RecyclerView.HORIZONTAL, false)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position % 3 == 2) 2 else 1
            }
        }
        recyclerView.layoutManager = gridLayoutManager
        val adapter = StaggeredProductCardRecyclerViewAdapter(listGoods)
        recyclerView.adapter = adapter
        val largePadding = resources.getDimensionPixelSize(R.dimen.shr_staggered_product_grid_spacing_large)
        val smallPadding = resources.getDimensionPixelSize(R.dimen.shr_staggered_product_grid_spacing_small)
        recyclerView.addItemDecoration(ProductGridItemDecoration(largePadding, smallPadding))
    }

    private fun getGoods() {
        val goodsName = resources.getStringArray(R.array.goods_name)
        val goodsImage = resources.obtainTypedArray(R.array.goods_image)
        val goodsPrice = resources.getStringArray(R.array.goods_price)
        for (index in goodsName.indices) {
            val goods = Goods(
                goodsName = goodsName[index],
                goodsImage = goodsImage.getResourceId(index, -1),
                goodsPrice = goodsPrice[index]
            )
            listGoods.add(goods)
        }
    }

}

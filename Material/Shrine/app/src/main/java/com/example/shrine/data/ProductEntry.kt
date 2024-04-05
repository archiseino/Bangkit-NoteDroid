package com.example.shrine.data

sealed class ProductEntry {
    data class FirstType(val title: String, val price: String, val imageUrl: Int) : ProductEntry()
    data class SecondType(val title: String, val price: String, val imageUrl: Int) : ProductEntry()
    data class ThirdType(val title: String, val price: String, val imageUrl: Int) : ProductEntry()
}

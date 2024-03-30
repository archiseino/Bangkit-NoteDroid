package com.example.shrine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.shrine.databinding.ShrMainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ShrMainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ShrMainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}

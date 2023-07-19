package com.example.slideapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.slideapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val model: SlideViewModel = ViewModelProvider(this@MainActivity)[SlideViewModel::class.java]

        model.squareColor.observe(this){
            val changedColor = Color.parseColor(it.toHexColor())
            binding.ivSquare.setBackgroundColor(changedColor)
            binding.btnBackgroundColor.setBackgroundColor(changedColor)
            binding.btnBackgroundColor.text = it.toHexColor()
        }

        binding.ivSquare.setOnTouchListener { _, _ ->
            binding.ivSquare.setImageResource(R.drawable.shape_borderline)
            true
        }

        binding.vSlide.setOnClickListener {
            binding.ivSquare.setImageResource(0)
        }

        binding.btnBackgroundColor.setOnClickListener {
            model.changeBackgroundColor()
        }
        binding.btnAlphaMinus.setOnClickListener {

        }
        binding.btnAlphaPlus.setOnClickListener {

        }
    }
}
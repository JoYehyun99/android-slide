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

        model.slide.observe(this) {
            binding.ivSquare.setBackgroundColor(Color.parseColor(it.color.getHexColor()))
            binding.btnBackgroundColor.setBackgroundColor(Color.parseColor(it.color.getHexColorForBtn()))
            binding.btnBackgroundColor.text = it.color.getHexColorForBtn()
            binding.etAlphaNum.setText(it.color.alpha.toString())
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
            model.removeOpacity()
        }
        binding.btnAlphaPlus.setOnClickListener {
            model.addOpacity()
        }
    }
}
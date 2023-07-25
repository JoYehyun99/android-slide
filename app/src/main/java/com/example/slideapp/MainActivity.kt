package com.example.slideapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.slideapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnSlideItemTouchListener {

    private lateinit var model: SlideViewModel
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val slideAdapter by lazy {
        SlideListAdapter(model, this@MainActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        model = ViewModelProvider(this)[SlideViewModel::class.java]
        val itemTouchHelper = ItemTouchHelper(ItemTouchCallback(slideAdapter))

        binding.rvLeft.adapter = slideAdapter
        binding.rvLeft.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvLeft.addItemDecoration(SlideItemDecoration())
        itemTouchHelper.attachToRecyclerView(binding.rvLeft)
        observeData()
        
        binding.ivSquare.setOnTouchListener { _, _ ->
            binding.ivSquare.setImageResource(R.drawable.shape_borderline)
            model.setSelected(true)
            true
        }
        binding.vSlide.setOnClickListener {
            binding.ivSquare.setImageResource(0)
            model.setSelected(false)
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
        binding.btnSlidePlus.setOnClickListener {
            model.addNewSlide()
        }
    }

    private fun observeData() {

        model.slide.observe(this) { slide ->
            binding.ivSquare.setBackgroundColor(Color.parseColor(slide.color.getHexColor()))
            binding.btnBackgroundColor.setBackgroundColor(Color.parseColor(slide.color.getHexColorForBtn()))
            binding.btnBackgroundColor.text = slide.color.getHexColorForBtn()
            binding.etAlphaNum.setText(slide.color.alpha.toString())
            slideAdapter.setNowSlide(slide)
        }

        model.isSelected.observe(this) {
            binding.btnBackgroundColor.isEnabled = it
            binding.btnAlphaMinus.isEnabled = it
            binding.btnAlphaPlus.isEnabled = it
        }

        model.slideList.observe(this) { slideList ->
            slideAdapter.setSlideList(slideList)
        }
    }

    override fun showSlide(slide: Slide, position: Int): Boolean {
        Log.d("slide", "select $position")
        model.switchTurn(position)
        return true
    }
}
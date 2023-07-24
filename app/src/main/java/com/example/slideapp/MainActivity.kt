package com.example.slideapp

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.slideapp.databinding.ActivityMainBinding
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity(), OnSlideItemTouchListener {

    private lateinit var model: SlideViewModel
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val slideAdapter by lazy {
        SlideListAdapter(model, this@MainActivity)
    }
    private var lastClickTime: Long = 0
    private val doubleClickTimeLimit: Long = 1000
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                Glide.with(this).asBitmap().load(uri).into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        val outputStream = ByteArrayOutputStream()
                        resource.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                        val byteArray = outputStream.toByteArray()
                        model.changeImage(byteArray)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })
            }
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

        binding.ivImage.setOnTouchListener { _, event ->
            binding.ivImage.setBackgroundResource(R.drawable.shape_borderline)
            model.setSelected(true)
            if (event.action == MotionEvent.ACTION_DOWN) {
                val currentTime = System.currentTimeMillis()
                val timeDelta = currentTime - lastClickTime
                if (timeDelta < doubleClickTimeLimit) {
                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
                lastClickTime = currentTime
            }
            true
        }

        binding.vSlide.setOnClickListener {
            binding.ivSquare.setImageResource(0)
            binding.ivImage.setBackgroundResource(0)
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
            when (slide) {
                is Slide.ImageSlide -> {
                    binding.ivSquare.visibility = View.INVISIBLE
                    binding.ivImage.visibility = View.VISIBLE
                    binding.btnBackgroundColor.setBackgroundColor(resources.getColor(R.color.white))
                    binding.btnBackgroundColor.text = ""
                    if (slide.img == null) {
                        binding.ivImage.setImageResource(R.drawable.baseline_image_search_24)
                    } else {
                        val byteArray = slide.img
                        Glide.with(this).load(byteArray).into(binding.ivImage)
                        binding.ivImage.imageAlpha = slide.color.getAlphaInt()
                    }
                }

                is Slide.SquareSlide -> {
                    binding.ivSquare.visibility = View.VISIBLE
                    binding.ivImage.visibility = View.INVISIBLE
                    binding.ivSquare.setBackgroundColor(Color.parseColor(slide.color.getHexColor()))
                    binding.btnBackgroundColor.setBackgroundColor(Color.parseColor(slide.color.getHexColorForBtn()))
                    binding.btnBackgroundColor.text = slide.color.getHexColorForBtn()
                }
            }
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
        model.switchTurn(position)
        return true
    }
}
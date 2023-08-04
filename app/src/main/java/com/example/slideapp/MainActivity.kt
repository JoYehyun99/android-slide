package com.example.slideapp

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.slideapp.databinding.ActivityMainBinding
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity(), OnSlideItemTouchListener {

    private lateinit var model: SlideViewModel
    private lateinit var binding: ActivityMainBinding
    private var lastClickTime: Long = 0
    private val doubleClickTimeLimit: Long = 1000
    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        model = ViewModelProvider(this)[SlideViewModel::class.java]
        binding.myViewModel = model
        binding.myActivity = this
        binding.lifecycleOwner = this

        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
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
    }

    override fun showSlide(slide: Slide, position: Int): Boolean {
        model.switchTurn(position)
        Log.d("slide_text","selected slide num = $position")
        return true
    }
}
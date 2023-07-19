package com.example.slideapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class SlideViewModel : ViewModel() {

    private val _squareColor: MutableLiveData<RGB> by lazy {
        MutableLiveData<RGB>().apply {
            value = getRandomColor()
        }
    }
    val squareColor: LiveData<RGB> = _squareColor

    private fun getRandomColor(): RGB {
        return RGB(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
    }

    fun changeBackgroundColor() {
        _squareColor.value = getRandomColor()
    }
}
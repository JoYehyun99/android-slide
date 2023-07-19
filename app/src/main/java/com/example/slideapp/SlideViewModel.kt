package com.example.slideapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class SlideViewModel : ViewModel() {

    private val _squareColor: MutableLiveData<ARGB> by lazy {
        MutableLiveData<ARGB>().apply {
            value = getRandomColor(10)
        }
    }
    val squareColor: LiveData<ARGB> = _squareColor

    private fun getRandomColor(alpha: Int): ARGB {
        return ARGB(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256), alpha)
    }

    fun changeBackgroundColor() {
        _squareColor.value = getRandomColor(_squareColor.value!!.alpha)
    }

    fun addOpacity() {
        if (_squareColor.value!!.alpha < 10) {
            _squareColor.value =
                _squareColor.value!!.copy(alpha = _squareColor.value!!.alpha.plus(1))
        }
    }

    fun removeOpacity() {
        if (_squareColor.value!!.alpha > 1) {
            _squareColor.value =
                _squareColor.value!!.copy(alpha = _squareColor.value!!.alpha.minus(1))
        }
    }
}
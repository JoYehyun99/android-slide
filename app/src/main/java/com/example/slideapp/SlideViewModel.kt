package com.example.slideapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SlideViewModel : ViewModel() {

    private val slideManager = SlideManager()
    private val nowSlideNum = 0

    private val _slide: MutableLiveData<Slide> by lazy {
        MutableLiveData<Slide>().apply {
            value = slideManager.addSlide()
        }
    }
    val slide: LiveData<Slide> = _slide

    fun changeBackgroundColor() {
        _slide.value = slideManager.changeBackgroundColor(nowSlideNum)
    }

    fun addOpacity() {
        if (slideManager.changeOpacity(nowSlideNum, 1)) {
            _slide.value = slideManager.getSlide(nowSlideNum)
        }
    }

    fun removeOpacity() {
        if (slideManager.changeOpacity(nowSlideNum, -1)) {
            _slide.value = slideManager.getSlide(nowSlideNum)
        }
    }


}
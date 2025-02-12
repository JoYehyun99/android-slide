package com.example.slideapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SlideViewModel() : ViewModel() {

    private val slideManager = SlideManager()
    private var nowSlideNum = 0

    private val _slide: MutableLiveData<Slide> by lazy {
        MutableLiveData<Slide>().apply {
            value = slideManager.addSlide()
        }
    }
    val slide: LiveData<Slide> = _slide
    private val _isSelected: MutableLiveData<Boolean> = MutableLiveData(false)
    val isSelected: LiveData<Boolean> = _isSelected
    private val _slideList: MutableLiveData<List<Slide>> by lazy {
        MutableLiveData<List<Slide>>(slideManager.getSlideList())
    }
    val slideList: LiveData<List<Slide>> = _slideList
    val isImageSlide: LiveData<Boolean> = Transformations.map(slide) { slide ->
        slide is ImageSlide
    }

    private val viewModelScope = CoroutineScope(Dispatchers.Main)

    fun changeBackgroundColor() {
        _slide.value = slideManager.changeBackgroundColor(nowSlideNum)
        _slideList.value = slideManager.getSlideList()
    }

    fun addOpacity() {
        if (slideManager.changeOpacity(nowSlideNum, 1)) {
            _slide.value = slideManager.getSlide(nowSlideNum)
            updateSlideList()
        }
    }

    fun removeOpacity() {
        if (slideManager.changeOpacity(nowSlideNum, -1)) {
            _slide.value = slideManager.getSlide(nowSlideNum)
            updateSlideList()
        }
    }

    fun setSelected(selected: Boolean) {
        _isSelected.value = selected
    }

    private fun updateSlideList() {
        _slideList.value = slideManager.getSlideList()
    }

    fun addNewSlide() {
        val newSlide = slideManager.addSlide()
        updateSlideList()
        _slide.value = newSlide
    }

    fun switchTurn(position: Int) {
        val newTurn = slideManager.getSlide(position)
        nowSlideNum = position
        _slide.value = newTurn
    }

    fun changeOrder(from: Int, to: Int) {
        val newOrder = slideManager.changeSlideOrder(from, to)
        _slideList.value = newOrder
    }

    fun changeImage(imageUri: ByteArray) {
        val newImage = slideManager.changeImage(nowSlideNum, imageUri)
        if (newImage != null) {
            updateSlideList()
            _slide.value = newImage
        }
    }

    fun addNewSlideFromServer(): Boolean {
        viewModelScope.launch {
            slideManager.addSlideFromServer() { result ->
                if (result) {
                    updateSlideList()
                }
            }
        }
        return true
    }
}
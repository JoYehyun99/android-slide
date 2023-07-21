package com.example.slideapp

interface OnSlideItemTouchListener {
    fun showSlide(slide: Slide, position: Int): Boolean
}
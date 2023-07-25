package com.example.slideapp

interface SlideItemFactory {
    val check: MutableSet<String>
    fun createSlide(alpha: Int): Slide
    fun getRandomId(): String
}
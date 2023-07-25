package com.example.slideapp

interface SlideItemFactory {
    val check: MutableSet<String>
    fun createSlide(side: Int, alpha: Int): Slide
    fun getRandomId(): String
}
package com.example.slideapp

interface SlideItemFactory {

    val check: MutableSet<String>
    fun createSlide(side: Int, color: ARGB): Slide
    fun getRandomId(): String
}
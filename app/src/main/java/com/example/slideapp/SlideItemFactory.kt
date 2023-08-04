package com.example.slideapp

interface SlideItemFactory {

    val duplicationCheck: MutableSet<String>
    fun createSlide(alpha: Int): Slide
    fun getRandomId(): String
}
package com.example.slideapp

interface SlideItemFactory {
    fun createSlide(side: Int, r: Int, g: Int, b: Int, alpha: Int): Slide
    fun getRandomId(): String{
        val charSet = ('a'..'z') + ('0'..'9')
        val result = (1..9).map { charSet.random() }
        return result.joinToString("").chunked(3).joinToString("-")
    }
}
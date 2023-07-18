package com.example.slideapp

interface SlideItemFactory {
    fun createSlide(side: Int, r: Int, g: Int, b: Int, alpha: Int): Slide
    fun getRandomId(): String {
        // 중복 처리 추가하기 - mutable set
        val charSet = ('a'..'z') + ('0'..'9')
        val result = (1..9).map { charSet.random() }
        return result.joinToString("").chunked(3).joinToString("-")
    }
}
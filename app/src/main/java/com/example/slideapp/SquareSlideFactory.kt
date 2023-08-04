package com.example.slideapp

import com.example.slideapp.ColorUtil.getRandomColor

class SquareSlideFactory() : SlideItemFactory {

    override val duplicationCheck: MutableSet<String> = mutableSetOf()
    private val SIZE = 100  // 임시 사이즈
    override fun createSlide(alpha: Int): Slide {
        return SquareSlide(getRandomId(), alpha, SIZE, getRandomColor())
    }

    override fun getRandomId(): String {
        val charSet = ('a'..'z') + ('0'..'9')
        var result = (1..9).map { charSet.random() }.joinToString("").chunked(3).joinToString("-")
        while (duplicationCheck.contains(result)) {
            result = (1..9).map { charSet.random() }.joinToString("").chunked(3).joinToString("-")
        }
        duplicationCheck.add(result)
        return result
    }

    fun createCustomSlide(id: String, size: Int, alpha: Int, color: RGB): SquareSlide {
        return SquareSlide(id, alpha, size, color)
    }
}
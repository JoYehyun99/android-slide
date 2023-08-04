package com.example.slideapp

import com.example.slideapp.ColorUtil.getRandomColor

class DrawingSlideFactory(): SlideItemFactory {

    override val duplicationCheck: MutableSet<String> = mutableSetOf()

    override fun createSlide(alpha: Int): Slide {
        return DrawingSlide(getRandomId(),alpha,getRandomColor())
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


}
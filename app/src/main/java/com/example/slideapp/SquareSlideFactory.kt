package com.example.slideapp

import kotlin.random.Random

class SquareSlideFactory() : SlideItemFactory {

    override val check: MutableSet<String> = mutableSetOf()
    override fun createSlide(side: Int, alpha: Int): Slide {
        return SquareSlide(getRandomId(), side, alpha,getRandomColor())
    }

    override fun getRandomId(): String {
        val charSet = ('a'..'z') + ('0'..'9')
        var result = (1..9).map { charSet.random() }.joinToString("").chunked(3).joinToString("-")
        while (check.contains(result)) {
            result = (1..9).map { charSet.random() }.joinToString("").chunked(3).joinToString("-")
        }
        check.add(result)
        return result
    }

    fun getRandomColor(): RGB {
        return RGB(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
    }
}
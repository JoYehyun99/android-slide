package com.example.slideapp

class SquareSlideFactory() : SlideItemFactory {

    override val check: MutableSet<String> = mutableSetOf()
    override fun createSlide(side: Int, color: ARGB): Slide {
        return Slide(getRandomId(), side, color)
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
}
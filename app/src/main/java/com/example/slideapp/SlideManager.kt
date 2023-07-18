package com.example.slideapp

class SlideManager {

    private val wholeSlides: MutableList<Slide> by lazy {
        mutableListOf<Slide>()
    }

    fun addSlide(side: Int, r: Int, g: Int, b: Int, alpha: Int): Slide {
        val squareFactory = SquareSlideFactory()
        wholeSlides.add(squareFactory.createSlide(side, r, g, b, alpha))
        return wholeSlides.last()
    }

    fun countTotalSlides(): Int{
        return wholeSlides.size
    }

    fun getSlide(idx: Int): Slide{
        return wholeSlides[idx]
    }

}
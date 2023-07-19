package com.example.slideapp

import kotlin.random.Random

class SlideManager {

    private val wholeSlides: MutableList<Slide> by lazy {
        mutableListOf<Slide>()
    }
    private val SIZE = 100  // 임시 사이즈

    private fun getRandomColor(alpha: Int): ARGB {
        return ARGB(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256), alpha)
    }

    fun addSlide(): Slide {
        val squareFactory = SquareSlideFactory()
        wholeSlides.add(squareFactory.createSlide(SIZE, getRandomColor(10)))
        return wholeSlides.last()
    }

    fun countTotalSlides(): Int {
        return wholeSlides.size
    }

    fun getSlide(idx: Int): Slide {
        return wholeSlides[idx]
    }

    fun changeBackgroundColor(idx: Int): Slide {
        wholeSlides[idx].color = getRandomColor(wholeSlides[idx].color.alpha)
        return wholeSlides[idx]
    }

    fun changeOpacity(idx: Int, n: Int): Boolean {
        if (n == 1) {
            if (wholeSlides[idx].color.alpha < 10) {
                wholeSlides[idx].color.alpha += 1
                return true
            }
            return false
        } else {
            if (wholeSlides[idx].color.alpha > 1) {
                wholeSlides[idx].color.alpha -= 1
                return true
            }
            return false
        }
    }

}
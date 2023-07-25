package com.example.slideapp

import kotlin.random.Random

class SlideManager {

    private val slideList: MutableList<Slide> = mutableListOf()
    private val SIZE = 100  // 임시 사이즈

    private fun getRandomColor(alpha: Int): ARGB {
        return ARGB(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256), alpha)
    }

    fun addSlide(): Slide {
        // todo 3 -5 : 정사각형 슬라이드 혹은 이미지 슬라이드 중 랜덤으로 생성
        val squareFactory = SquareSlideFactory()
        slideList.add(squareFactory.createSlide(SIZE, getRandomColor(10)))
        return slideList.last()
    }

    fun countTotalSlides(): Int {
        return slideList.size
    }

    fun getSlide(idx: Int): Slide {
        return slideList[idx]
    }

    fun changeBackgroundColor(idx: Int): Slide {
        val slideItem = slideList[idx] as Slide.SquareSlide
        val newSlide = slideItem.copy(color = getRandomColor(slideList[idx].color.alpha))
        slideList[idx] = newSlide
        return newSlide
    }

    fun changeOpacity(idx: Int, n: Int): Boolean {
        val slideItem = slideList[idx] as Slide.SquareSlide
        var newAlpha = slideItem.color.alpha

        if (n == 1) {   // add
            if (newAlpha < 10) {
                newAlpha += 1
                val newColor = slideItem.color.copy(alpha = newAlpha)
                val newSlide = slideItem.copy(color = newColor)
                slideList[idx] = newSlide
                return true
            }
            return false
        } else {    // sub
            if (newAlpha > 1) {
                newAlpha -= 1
                val newColor = slideItem.color.copy(alpha = newAlpha)
                val newSlide = slideItem.copy(color = newColor)
                slideList[idx] = newSlide
                return true
            }
            return false
        }
    }

    fun getSlideList(): List<Slide> {
        return slideList.toList()
    }

    fun changeSlideOrder(from: Int, to: Int): List<Slide> {
        val slideItem = slideList[from]
        slideList.removeAt(from)
        slideList.add(to, slideItem)
        return getSlideList()
    }
}
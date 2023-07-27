package com.example.slideapp

import kotlin.random.Random

class SlideManager {

    private val slideList: MutableList<Slide> = mutableListOf()
    private val SIZE = 100  // 임시 사이즈
    private val factories: List<SlideItemFactory> by lazy {
        listOf(SquareSlideFactory(), ImageSlideFactory())
    }

    private fun getRandomColor(alpha: Int): ARGB {
        return ARGB(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256), alpha)
    }

    fun addSlide(): Slide {
        val randomFactory = factories.random()
        val newSlide = randomFactory.createSlide(SIZE, getRandomColor(10))
        slideList.add(newSlide)
        return newSlide
    }

    fun countTotalSlides(): Int {
        return slideList.size
    }

    fun getSlide(idx: Int): Slide {
        return slideList[idx]
    }

    fun changeBackgroundColor(idx: Int): Slide {
        return when (slideList[idx]) {
            is Slide.SquareSlide -> {
                val slideItem = slideList[idx] as Slide.SquareSlide
                val newSlide = slideItem.copy(color = getRandomColor(slideList[idx].color.alpha))
                slideList[idx] = newSlide
                newSlide
            }

            is Slide.ImageSlide -> {
                slideList[idx]
            }
        }
    }

    private fun getNewOpacity(value: Int, mode: Int): Int{
        if(mode == 1){  // add
            if(value < 10)  return value + 1
        } else {    // sub
            if(value > 1) return value - 1
        }
        return value
    }

    fun changeOpacity(idx: Int, n: Int): Boolean {
        when(val slideItem = slideList[idx]){
            is Slide.SquareSlide -> {
                val newAlpha = getNewOpacity(slideItem.color.alpha, n)
                if(newAlpha != slideItem.color.alpha) {
                    val newColor = slideItem.color.copy(alpha = newAlpha)
                    val newSlide = slideItem.copy(color = newColor)
                    slideList[idx] = newSlide
                    return true
                }
                return false
            }
            is Slide.ImageSlide -> {
                val newAlpha = getNewOpacity(slideItem.color.alpha, n)
                if(newAlpha != slideItem.color.alpha) {
                    val newColor = slideItem.color.copy(alpha = newAlpha)
                    val newSlide = slideItem.copy(color = newColor)
                    slideList[idx] = newSlide
                    return true
                }
                return false
            }
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
    fun changeImage(idx: Int, imageUri: ByteArray): Slide?{
        val slideItem = slideList[idx]
        if (slideItem is Slide.ImageSlide){
            val newSlide = slideItem.copy(img = imageUri)
            slideList[idx] = newSlide
            return newSlide
        } else {
            return null
        }

    }
}
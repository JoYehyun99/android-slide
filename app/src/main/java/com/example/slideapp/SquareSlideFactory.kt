package com.example.slideapp

class SquareSlideFactory : SlideItemFactory {
    override fun createSlide(side: Int, r: Int, g: Int, b: Int, alpha: Int): Slide {
        return Slide(getRandomId(), side, RGB(r,g,b), alpha)
    }
}
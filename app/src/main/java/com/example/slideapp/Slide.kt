package com.example.slideapp

sealed class Slide(
    open val id: String,
    open val side: Int,
    open val color: ARGB
) {
    data class SquareSlide(
        override val id: String,
        override val side: Int,
        override val color: ARGB
    ) : Slide(id, side, color)

    data class ImageSlide(
        override val id: String,
        override val side: Int,
        override val color: ARGB,
        val img: String?
    ) : Slide(id, side, color)
}
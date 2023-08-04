package com.example.slideapp

import android.graphics.Path

sealed class Slide(
    open val id: String,
    open val alpha: Int
)

data class SquareSlide(
    override val id: String,
    override val alpha: Int,
    val side: Int,
    val color: RGB,
) : Slide(id, alpha) {
    private fun getAlphaString(): String {
        return when (alpha) {
            1 -> Alpha.ALPHA_1.code
            2 -> Alpha.ALPHA_2.code
            3 -> Alpha.ALPHA_3.code
            4 -> Alpha.ALPHA_4.code
            5 -> Alpha.ALPHA_5.code
            6 -> Alpha.ALPHA_6.code
            7 -> Alpha.ALPHA_7.code
            8 -> Alpha.ALPHA_8.code
            9 -> Alpha.ALPHA_9.code
            10 -> Alpha.ALPHA_10.code
            else -> ""
        }
    }

    fun getHexColor(): String {
        val hexRGB = color.getHexRGBColor().substring(1)
        val hexAlpha = getAlphaString()
        return "#$hexAlpha$hexRGB"
    }
}

data class ImageSlide(
    override val id: String,
    override val alpha: Int,
    val img: ByteArray?
) : Slide(id, alpha) {

    fun getAlphaInt(): Int {
        return when (alpha) {
            1 -> Alpha.ALPHA_1.value
            2 -> Alpha.ALPHA_2.value
            3 -> Alpha.ALPHA_3.value
            4 -> Alpha.ALPHA_4.value
            5 -> Alpha.ALPHA_5.value
            6 -> Alpha.ALPHA_6.value
            7 -> Alpha.ALPHA_7.value
            8 -> Alpha.ALPHA_8.value
            9 -> Alpha.ALPHA_9.value
            10 -> Alpha.ALPHA_10.value
            else -> 0
        }
    }
}

data class DrawingSlide(
    override val id: String,
    override val alpha: Int,
    val color: RGB,
    var path: Path? = null,
    val border: Array<Float> = Array(4) { 0.0f },    // minX, maxY, maxX, minY
    var isDrawable: Boolean = true
) : Slide(id, alpha)
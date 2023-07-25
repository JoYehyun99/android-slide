package com.example.slideapp

data class RGB(
    val R: Int,
    val G: Int,
    val B: Int
) {
    private val RADIX = 16

    fun getHexRGBColor(): String {
        val hexR = R.toString(RADIX).padStart(2, '0')
        val hexG = G.toString(RADIX).padStart(2, '0')
        val hexB = B.toString(RADIX).padStart(2, '0')
        return "#$hexR$hexG$hexB"
    }
}
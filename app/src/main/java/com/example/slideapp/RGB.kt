package com.example.slideapp

data class RGB(
    var r: Int,
    var g: Int,
    var b: Int
) {
    val RADIX = 16
    fun toHexColor(): String {
        val hexR = r.toString(RADIX).padStart(2, '0')
        val hexG = g.toString(RADIX).padStart(2, '0')
        val hexB = b.toString(RADIX).padStart(2, '0')
        return "#$hexR$hexG$hexB"
    }
}
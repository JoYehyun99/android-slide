package com.example.slideapp

data class ARGB(
    val r: Int,
    val g: Int,
    val b: Int,
    val alpha: Int
) {
    val RADIX = 16
    fun getHexColor(): String {
        val hexRGB = getHexColorForBtn()
        val hexAlpha = getAlphaString()
        return "#$hexAlpha$hexRGB"
    }

    fun getHexColorForBtn(): String {
        val hexR = r.toString(RADIX).padStart(2, '0')
        val hexG = g.toString(RADIX).padStart(2, '0')
        val hexB = b.toString(RADIX).padStart(2, '0')
        return "#$hexR$hexG$hexB"
    }

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
}
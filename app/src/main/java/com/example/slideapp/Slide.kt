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
        val img: ByteArray?
    ) : Slide(id, side, color) {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as ImageSlide

            if (id != other.id) return false

            return true
        }

        override fun hashCode(): Int {
            var result = id.hashCode()
            result = 31 * result + img.contentHashCode()
            return result
        }
    }
}
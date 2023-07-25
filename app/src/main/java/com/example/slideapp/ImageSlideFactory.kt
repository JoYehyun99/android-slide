package com.example.slideapp

class ImageSlideFactory(): SlideItemFactory {
    override val check: MutableSet<String> = mutableSetOf()
    override fun createSlide(alpha: Int): Slide {
        return ImageSlide(getRandomId(),alpha,null)
    }

    override fun getRandomId(): String {
        val charSet = ('a'..'z') + ('0'..'9')
        var result = (1..9).map { charSet.random() }.joinToString("").chunked(3).joinToString("-")
        while (check.contains(result)) {
            result = (1..9).map { charSet.random() }.joinToString("").chunked(3).joinToString("-")
        }
        check.add(result)
        return result
    }

    fun createCustomSlide(id: String, alpha: Int, imgUrl: ByteArray): ImageSlide{
        return ImageSlide(id,alpha,imgUrl)
    }
}
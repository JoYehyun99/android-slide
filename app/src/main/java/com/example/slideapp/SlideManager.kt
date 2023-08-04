package com.example.slideapp

import android.util.Log
import com.example.slideapp.ColorUtil.getRandomColor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SlideManager {

    private val slideList: MutableList<Slide> = mutableListOf()
    private val squareFactory = SquareSlideFactory()
    private val imageFactory = ImageSlideFactory()
    private val drawingFactory = DrawingSlideFactory()
    private val factories: List<SlideItemFactory> by lazy {
        listOf(squareFactory, imageFactory, drawingFactory)
    }
    private val links = listOf("image-slides.json", "square-only-slides.json")
    private val ALPHA_DEFAULT = 10

    fun addSlide(): Slide {
        val randomFactory = factories.random()
        val newSlide = randomFactory.createSlide(ALPHA_DEFAULT)
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
        return when (val slideItem = slideList[idx]) {
            is SquareSlide -> {
                val newSlide = slideItem.copy(color = getRandomColor())
                slideList[idx] = newSlide
                newSlide
            }

            is ImageSlide -> {
                slideItem
            }

            is DrawingSlide -> {
                val newSlide = slideItem.copy(color = getRandomColor())
                slideList[idx] = newSlide
                newSlide
            }
        }
    }

    private fun getNewOpacity(value: Int, mode: Int): Int {
        if (mode == 1) {  // add
            if (value < 10) return value + 1
        } else {    // sub
            if (value > 1) return value - 1
        }
        return value
    }

    fun changeOpacity(idx: Int, n: Int): Boolean {
        when (val slideItem = slideList[idx]) {
            is SquareSlide -> {
                val newAlpha = getNewOpacity(slideItem.alpha, n)
                if (newAlpha != slideItem.alpha) {
                    val newSlide = slideItem.copy(alpha = newAlpha)
                    slideList[idx] = newSlide
                    return true
                }
                return false
            }

            is ImageSlide -> {
                val newAlpha = getNewOpacity(slideItem.alpha, n)
                if (newAlpha != slideItem.alpha) {
                    val newSlide = slideItem.copy(alpha = newAlpha)
                    slideList[idx] = newSlide
                    return true
                }
                return false
            }

            is DrawingSlide -> {
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

    fun changeImage(idx: Int, imageUri: ByteArray): Slide? {
        val slideItem = slideList[idx]
        if (slideItem is ImageSlide) {
            val newSlide = slideItem.copy(img = imageUri)
            slideList[idx] = newSlide
            return newSlide
        } else {
            return null
        }
    }

    fun addSlideFromServer(callback: (Boolean) -> Unit) {
        val slideData = SlideObject.getRetrofitService().getSlide(links.random())
        slideData.enqueue(object : Callback<JsonData> {
            override fun onResponse(call: Call<JsonData>, response: Response<JsonData>) {
                if (response.isSuccessful) {
                    val slides = response.body()!!.slides
                    slides.forEach { slide ->
                        when (slide.type) {
                            "Square" -> {
                                val color = slide.color!!
                                val newSlide = squareFactory.createCustomSlide(
                                    slide.id,
                                    slide.size!!,
                                    slide.alpha,
                                    RGB(color.R, color.G, color.B)
                                )
                                slideList.add(newSlide)
                                callback(true)
                            }

                            "Image" -> {
                                imageFactory.createCustomSlide(
                                    slide.id,
                                    slide.alpha,
                                    slide.url!!
                                ) { newSlide ->
                                    if (newSlide != null) {
                                        slideList.add(newSlide)
                                        callback(true)
                                    } else {
                                        callback(false)
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Log.d("getData", "no response")
                    callback(false)
                }
            }

            override fun onFailure(call: Call<JsonData>, t: Throwable) {
                Log.d("getData", "response fail")
            }

        })
    }
}
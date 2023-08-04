package com.example.slideapp

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageSlideFactory() : SlideItemFactory {

    override val duplicationCheck: MutableSet<String> = mutableSetOf()
    override fun createSlide(alpha: Int): Slide {
        return ImageSlide(getRandomId(), alpha, null)
    }

    override fun getRandomId(): String {
        val charSet = ('a'..'z') + ('0'..'9')
        var result = (1..9).map { charSet.random() }.joinToString("").chunked(3).joinToString("-")
        while (duplicationCheck.contains(result)) {
            result = (1..9).map { charSet.random() }.joinToString("").chunked(3).joinToString("-")
        }
        duplicationCheck.add(result)
        return result
    }

    fun createCustomSlide(id: String, alpha: Int, imgUrl: String, callback: (ImageSlide?) -> Unit) {
        val imageData = SlideObject.getRetrofitService().getImage(imgUrl)
        imageData.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let {
                        val imgByte = it.bytes()
                        val newSlide = ImageSlide(id, alpha, imgByte)
                        callback(newSlide)
                    }
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback(null)
            }
        })
    }
}
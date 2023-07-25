package com.example.slideapp

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface SlideService {
    @GET("jk/softeer-bootcamp/{SlideType}")
    fun getSlide(@Path("SlideType") slideType: String): Call<JsonData>

    @GET
    fun getImage(@Url url: String): Call<ResponseBody>
}
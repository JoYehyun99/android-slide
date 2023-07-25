package com.example.slideapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SlideService {
    @GET("jk/softeer-bootcamp/{SlideType}")
    fun getSlide(@Path("SlideType") slideType: String): Call<JsonData>
}
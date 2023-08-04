package com.example.slideapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SlideObject {

    private const val BASE_URL = "https://public.codesquad.kr/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getRetrofitService(): SlideService {
        return getRetrofit().create(SlideService::class.java)
    }
}
package com.example.slideapp

data class JsonData(
    val title: String,
    val creator: String,
    val slides: List<JsonSlide>
)
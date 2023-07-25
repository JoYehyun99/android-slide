package com.example.slideapp

data class JsonSlide(
    val type: String,
    val id: String,
    val url: String?,
    val size: Int?,
    val color: RGB?,
    val alpha: Int
)

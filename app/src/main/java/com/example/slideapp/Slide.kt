package com.example.slideapp

class Slide(
    val id: String,
    var side: Int,
    var color: RGB,
    var alpha: Int
) {
    override fun toString(): String = "($id), Side:$side, R:${color.r}, G:${color.g}, B:${color.b}, Alpha: $alpha"
}
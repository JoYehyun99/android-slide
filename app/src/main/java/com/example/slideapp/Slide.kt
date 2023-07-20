package com.example.slideapp

data class Slide(
    val id: String,
    var side: Int,
    var color: ARGB
) {
    override fun toString(): String =
        "($id), Side:$side, R:${color.r}, G:${color.g}, B:${color.b}, Alpha: ${color.alpha}"
}
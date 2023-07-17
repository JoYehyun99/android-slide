package com.example.slideapp

class Slide(
    val id: String,
    var side: Int,
    var r: Int,
    var g: Int,
    var b: Int,
    var alpha: Int
) {
    override fun toString(): String = "($id), Side:$side, R:$r, G:$g, B:$b, Alpha: $alpha"
}
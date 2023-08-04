package com.example.slideapp

import kotlin.random.Random

object ColorUtil {
    fun getRandomColor(): RGB {
        return RGB(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
    }
}
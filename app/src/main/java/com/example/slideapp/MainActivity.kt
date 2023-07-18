package com.example.slideapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val squareFactory = SquareSlideFactory()
        Log.d("square_slide","Rect1 ${squareFactory.createSlide(216,245,0,245,9)}")
        Log.d("square_slide","Rect2 ${squareFactory.createSlide(384,43,124,95,5)}")
        Log.d("square_slide","Rect3 ${squareFactory.createSlide(108,98,244,15,7)}")
        Log.d("square_slide","Rect4 ${squareFactory.createSlide(233,125,39,99,1)}")
    }
}
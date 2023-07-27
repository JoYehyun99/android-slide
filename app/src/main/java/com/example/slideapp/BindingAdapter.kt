package com.example.slideapp

import android.graphics.Color
import android.widget.Button
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("imgBgr", "selectedSquare")
    fun setColor(view: ImageView, slide: Slide, selected: Boolean) {

        when (slide) {
            is SquareSlide -> {
                view.setBackgroundColor(Color.parseColor(slide.getHexColor()))
                view.setImageResource(0)
            }

            is ImageSlide -> {
            }
        }
        if (selected) {
            view.setImageResource(R.drawable.shape_borderline)
        } else {
            view.setImageResource(0)
        }
    }

    @JvmStatic
    @BindingAdapter("imgSrc", "selectedImg")
    fun setImage(view: ImageView, slide: Slide, selected: Boolean) {

        when (slide) {
            is SquareSlide -> {
            }

            is ImageSlide -> {
                view.setBackgroundResource(R.color.white)
                view.imageAlpha = slide.getAlphaInt()
                if (slide.img == null) {
                    view.setImageResource(R.drawable.baseline_image_search_24)
                } else {
                    val byteArray = slide.img
                    Glide.with(view.context).load(byteArray).into(view)
                    view.imageAlpha = slide.getAlphaInt()
                }
            }
        }
        if (selected) {
            view.setBackgroundResource(R.drawable.shape_borderline)
        } else {
            view.setBackgroundResource(0)
        }
    }

    @JvmStatic
    @BindingAdapter("bgrBtn")
    fun setBackgroundBtn(button: Button, slide: Slide) {

        when (slide) {
            is SquareSlide -> {
                button.setBackgroundColor(Color.parseColor(slide.color.getHexRGBColor()))
                button.text = slide.color.getHexRGBColor()
            }

            is ImageSlide -> {
                button.setBackgroundColor(Color.WHITE)
                button.text = ""
            }
        }
    }
}
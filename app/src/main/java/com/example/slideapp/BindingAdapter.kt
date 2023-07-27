package com.example.slideapp

import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
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

    @JvmStatic
    @BindingAdapter("slideIcon")
    fun setSlideItemIcon(view: ImageView, slide: Slide) {
        when (slide) {
            is ImageSlide -> {
                view.setImageResource(R.drawable.baseline_photo_24)
            }

            is SquareSlide -> {
                view.setImageResource(R.drawable.baseline_fit_screen_24)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("slides", "slideTurn")
    fun setSlideTurn(view: View, slideItem: Slide, nowSlide: Slide) {
        if (nowSlide?.id == slideItem.id) {
            view.setBackgroundResource(R.color.selected_bgr)
        } else {
            view.setBackgroundResource(R.color.white)
        }
    }

    @JvmStatic
    @BindingAdapter("context", "model", "listData", "nowSlide")
    fun bindRecyclerView(
        recyclerView: RecyclerView,
        context: OnSlideItemTouchListener,
        model: SlideViewModel,
        data: List<Slide>,
        nowSlide: Slide
    ) {

        if (recyclerView.adapter == null) {
            val slideAdapter = SlideListAdapter(model, context)
            recyclerView.adapter = slideAdapter
            val itemTouchHelper = ItemTouchHelper(ItemTouchCallback(slideAdapter))
            recyclerView.addItemDecoration(SlideItemDecoration())
            itemTouchHelper.attachToRecyclerView(recyclerView)
        }
        (recyclerView.adapter as? SlideListAdapter)?.setSlideList(data)
        (recyclerView.adapter as? SlideListAdapter)?.setNowSlide(nowSlide)
    }

}
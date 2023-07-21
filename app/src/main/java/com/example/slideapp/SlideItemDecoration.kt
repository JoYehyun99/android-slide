package com.example.slideapp

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SlideItemDecoration(): RecyclerView.ItemDecoration() {

    private val DIVIDER = 5

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = DIVIDER
    }
}
package com.filipkowicz.examples.ui.main

import android.graphics.Rect
import android.view.View
import androidx.annotation.Dimension
import androidx.recyclerview.widget.RecyclerView

class InsetItemDecoration(@Dimension private val padding: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = padding
        outRect.right = padding
        outRect.bottom = padding
        outRect.top = padding
    }
}
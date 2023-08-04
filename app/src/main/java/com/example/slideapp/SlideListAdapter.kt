package com.example.slideapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.slideapp.databinding.SlideItemBinding

class SlideListAdapter(
    private val model: SlideViewModel,
    private val listener: OnSlideItemTouchListener
) : RecyclerView.Adapter<SlideListAdapter.SlideListViewHolder>(),
    ItemTouchCallback.ItemTouchHelperListener {

    private var slideList: MutableList<Slide> = mutableListOf()
    private var nowSlide: Slide? = null
    private var popupMenu: PopupMenu? = null

    fun setSlideList(itemList: List<Slide>) {
        slideList = itemList.toMutableList()
    }

    fun setNowSlide(slide: Slide) {
        if (nowSlide == null) {
            nowSlide = slide
        } else {
            if (nowSlide != slide) {
                val beforeIdx = slideList.indexOf(nowSlide)
                nowSlide = slide
                notifyItemChanged(beforeIdx)
                notifyItemChanged(slideList.indexOf(slide))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideListViewHolder {
        val binding = SlideItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SlideListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return slideList.size
    }

    override fun onBindViewHolder(holder: SlideListViewHolder, position: Int) {
        holder.bind(slideList[position], position)
    }

    inner class SlideListViewHolder(private val binding: SlideItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Slide, index: Int) {
            binding.slideItem = item
            binding.idx = index
            binding.clickListener = listener
            binding.longClickListener = View.OnLongClickListener {
                showMenu(it, index)
                true
            }
            binding.nowSlide = nowSlide
        }
    }

    override fun onItemMove(from: Int, to: Int) { // 4 -> 0
        if (from != to && to < slideList.size && to > -1) {
            val slideItem = slideList[from]
            slideList.removeAt(from)
            slideList.add(to, slideItem)
            notifyItemMoved(from, to)
            if(from < to){
                for (i in from .. to){
                    notifyItemChanged(i)
                }
            } else {
                for (i in to .. from){
                    notifyItemChanged(i)
                }
            }
            model.changeOrder(from, to)
        }
    }

    private fun showMenu(view: View, position: Int) {
        if (popupMenu == null) {
            popupMenu = PopupMenu(view.context, view)
            popupMenu?.menuInflater?.inflate(R.menu.menu_option, popupMenu?.menu)

        }
        popupMenu?.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_send_to_back -> {
                    onItemMove(position, (slideList.size - 1))
                }

                R.id.menu_send_backward -> {
                    onItemMove(position, (position + 1))
                }

                R.id.menu_send_forward -> {
                    onItemMove(position, (position - 1))
                }

                R.id.menu_send_to_front -> {
                    onItemMove(position, 0)
                }
            }
            true
        }
        popupMenu?.show()
    }
}
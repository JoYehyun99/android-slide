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

    fun setSlideList(itemList: List<Slide>) {
        slideList = itemList.toMutableList()
        notifyDataSetChanged()
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
        val slideItem = slideList[position]

        holder.idxText.text = (position + 1).toString()
        when (slideItem) {
            is Slide.SquareSlide -> {
                holder.imgIcon.setImageResource(R.drawable.baseline_fit_screen_24)
            }

            is Slide.ImageSlide -> {
                holder.imgIcon.setImageResource(R.drawable.baseline_photo_24)
            }
        }
        holder.itemView.setOnClickListener {
            listener.showSlide(slideItem, position)
        }
        holder.itemView.setOnLongClickListener {
            showMenu(it, position)
            true
        }
        if (nowSlide?.id == slideItem.id) {
            holder.itemView.setBackgroundResource(R.color.selected_bgr)
        } else {
            holder.itemView.setBackgroundResource(R.color.white)
        }
    }

    inner class SlideListViewHolder(binding: SlideItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idxText = binding.tvIdx
        val imgIcon = binding.ivSlideIcon
    }

    override fun onItemMove(from: Int, to: Int) {
        if(from != to && to < slideList.size && to > -1){
            val slideItem = slideList[from]
            slideList.removeAt(from)
            slideList.add(to, slideItem)
            notifyItemMoved(from, to)
            notifyItemChanged(to)
            notifyItemChanged(from)
            model.changeOrder(from, to)
        }
    }

    private fun showMenu(view: View, position: Int) {
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.menuInflater.inflate(R.menu.menu_option, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_send_to_back -> {
                    onItemMove(position, (slideList.size - 1))
                }

                R.id.menu_send_backward -> {
                    onItemMove(position,(position+1))
                }

                R.id.menu_send_forward -> {
                    onItemMove(position,(position-1))
                }

                R.id.menu_send_to_front -> {
                    onItemMove(position, 0)
                }
            }
            true
        }
        popupMenu.show()
    }
}